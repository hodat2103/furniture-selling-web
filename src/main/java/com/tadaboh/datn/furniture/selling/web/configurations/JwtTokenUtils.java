package com.tadaboh.datn.furniture.selling.web.configurations;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey; // key
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;// thời gian sống
    @Value("${jwt.auth.refresh_token.expires_in}")
    private int refreshTokenExpiresIn; // Thời gian sống của refresh token (nên dài hơn access token)
    private String header = "Authorization";// Header mặc định chứa token
    private String startAuthHeader = "Bearer ";// Prefix mặc định của token trong header
    private Set<String> backListedToken = Collections.synchronizedSet(new HashSet<>()); // các token đã logout

    /**
     * Sinh JWT token cho người dùng.
     *
     * @param userName Tên người dùng để tạo token.
     * @return JWT token dưới dạng chuỗi.
     */
    public String generateToken(String userName) {
        return Jwts.builder()
                .issuer(appName)// tổ chức phát hành
                .subject(userName) // chủ thể đc phát hành
                .issuedAt(new Date()) // thời điểm phát hành
                .expiration(generateExpirationDate()) // thời điểm hết hạn
                .signWith(getSigningKey()) // chỉ máy chủ mới có thể ký và xác thực
                .compact(); // hoàn tất
    }

    /**
     * Lấy khóa bí mật dùng để ký JWT token.
     *
     * @return Key đối tượng khóa bí mật đã được mã hóa bằng thuật toán HMAC-SHA.
     */
    private Key getSigningKey() {
        byte[] keysBytes = Decoders.BASE64.decode(secretKey); // giải mã một chuỗi khóa từ định dạng base64
        return Keys.hmacShaKeyFor(keysBytes); // tạo một đối tượng Key được mã hóa bằng thuật toán HMAC-SHA
    }

    /**
     * Tạo ngày hết hạn cho token.
     *
     * @return Ngày hết hạn của token.
     */
    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L); // thời điểm phát hành + với thời gian sống
    }

    /**
     * Lấy token từ header của yêu cầu HTTP.
     *
     * @param request Đối tượng HttpServletRequest.
     * @return Token nếu có trong header, nếu không thì trả về null.
     */
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (null != authHeader && authHeader.startsWith(startAuthHeader)) {
            return authHeader.substring(7);
        }
        return authHeader;
    }
    /**
     * Kiểm tra xem token có trong blacklist không
     */
    public boolean isTokenBlacklisted(String token) {
        return backListedToken.contains(token);
    }

    /**
     * Kiểm tra tính hợp lệ của token.
     *
     * @param token       Token cần kiểm tra.
     * @param userDetails Thông tin người dùng để so sánh với token.
     * @return true nếu token hợp lệ, ngược lại false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null || isTokenBlacklisted(token)) {
            log.warn("Token cannot validate because token null or token has logout");
            return false;
        }
        try {
            final String userName = getUserNameFromToken(token).toLowerCase();
            // Kiểm tra các điều kiện: không null, khớp với userDetails và token chưa hết hạn thì true
            return userName != null
                    && userName.equals(userDetails.getUsername())
                    && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            log.warn("Token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            log.warn("JWT processing error: " + e.getMessage());
        } catch (Exception e) {
            log.warn("Unexpected error during token validation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Kiểm tra xem token có hết hạn không.
     *
     * @param token Token cần kiểm tra.
     * @return true nếu token đã hết hạn, ngược lại false.
     */
    private boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Token đã hết hạn");
            return true; // Nếu token null hoặc rỗng, coi như đã hết hạn
        }
        Date expireDate = getExpireDateToken(token);
        return expireDate.before(new Date());
    }

    /**
     * Lấy ngày hết hạn của token.
     *
     * @param token Token cần lấy thông tin hết hạn.
     * @return Ngày hết hạn của token.
     */
    private Date getExpireDateToken(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (ExpiredJwtException e) {
            expireDate = null;
            log.warn("Token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            expireDate = null;
            log.warn("Token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            expireDate = null;
            log.warn("Token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            expireDate = null;
            log.warn("JWT processing error: " + e.getMessage());
        } catch (Exception e) {
            expireDate = null;
            log.warn("Unexpected error during token expiration retrieval: " + e.getMessage());
        }
        return expireDate;
    }

    /**
     * Lấy giá trị của header "Authorization" từ yêu cầu HTTP.
     *
     * @param request Đối tượng HttpServletRequest.
     * @return Giá trị của header "Authorization".
     */
    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(header);
    }

    /**
     * Lấy tên người dùng từ token.
     *
     * @param authToken JWT token cần lấy tên người dùng.
     * @return Tên người dùng được lưu trong token.
     */
    public String getUserNameFromToken(String authToken) {
        String userName;
        try {
            final Claims claims = this.getAllClaimsFromToken(authToken);
            userName = claims.getSubject();
        } catch (ExpiredJwtException e) {
            userName = null;
            log.warn("Token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            userName = null;
            log.warn("Token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            userName = null;
            log.warn("Token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            userName = null;
            log.warn("JWT processing error: " + e.getMessage());
        } catch (Exception e) {
            userName = null;
            log.warn("Unexpected error during token username extraction: " + e.getMessage());
        }
        return userName;
    }

    /**
     * Lấy tất cả các claims từ token.
     *
     * @param token JWT token cần lấy thông tin claims.
     * @return Claims chứa thông tin từ token.
     */
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Tạo refresh token cho người dùng.
     *
     * @param userName Tên người dùng
     * @return Refresh token
     */
    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + refreshTokenExpiresIn * 1000L))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Xử lý logout bằng cách thêm token vào blacklist
     */
    public void logout(String token) {
        if (token != null && !token.isEmpty()) {
            backListedToken.add(token);
        }
        log.info("logout with token" + token);
    }

    /**
     * Tạo token mới từ refresh token
     *
     * @param refreshToken Refresh token hiện tại
     * @return Token mới hoặc null nếu refresh token không hợp lệ
     */
    public String refreshToken(String refreshToken) {
        try {
            Claims claims = getAllClaimsFromToken(refreshToken);
            String userName = claims.getSubject();

            // Checking refresh token valid ?
            if (userName != null && !isTokenExpired(refreshToken)) {
                log.info("refresh Token done !");
                return generateRefreshToken(userName);
            }
        } catch (ExpiredJwtException e) {
            log.warn("Refresh token has expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Refresh token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            log.warn("Refresh token signature is invalid: " + e.getMessage());
        } catch (JwtException e) {
            log.warn("JWT processing error during refresh token: " + e.getMessage());
        } catch (Exception e) {
            log.warn("Unexpected error during refresh token: " + e.getMessage());
        }
        return null;
    }

    // Thêm phương thức định kỳ xóa các token hết hạn khỏi blacklist
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Chạy mỗi 24 giờ
    public void cleanupBlacklist() {
        backListedToken.removeIf(token -> {
            try {
                log.info("Xóa blacklist token done !");
                return isTokenExpired(token);
            } catch (ExpiredJwtException e) {
                log.warn("Token expired during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (MalformedJwtException e) {
                log.warn("Malformed token during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (SignatureException e) {
                log.warn("Signature invalid during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (JwtException e) {
                log.warn("JWT error during blacklist cleanup: " + e.getMessage());
                return true;
            } catch (Exception e) {
                log.warn("Unexpected error during blacklist cleanup: " + e.getMessage());
                return true;
            }
        });
    }
}