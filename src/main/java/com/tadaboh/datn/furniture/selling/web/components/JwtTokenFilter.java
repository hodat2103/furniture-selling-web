package com.tadaboh.datn.furniture.selling.web.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadaboh.datn.furniture.selling.web.configurations.JwtTokenUtils;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Khởi tạo bộ lọc xác thực JWT với JWTTokenHelper và UserDetailsService.
     *
     * @param jwtTokenUtils     Giúp tạo và kiểm tra JWT token.
     * @param userDetailsService Dịch vụ lấy thông tin người dùng từ cơ sở dữ liệu.
     */


//    public JwtTokenFilter(UserDetailsService userDetailsService, JwtTokenUtils jwtTokenUtils) {
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtils = jwtTokenUtils;
//    }

    /**
     * Kiểm tra JWT token trong yêu cầu HTTP, xác thực người dùng nếu token hợp lệ.
     *
     * @param request Yêu cầu HTTP.
     * @param response Phản hồi HTTP.
     * @param filterChain Chuỗi bộ lọc để tiếp tục xử lý yêu cầu.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý yêu cầu.
     * @throws IOException Nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
            if (null == authHeader || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.clearContext();
        try {
            String authToken = jwtTokenUtils.getToken(request); // Lấy token
            if (null != authToken) {
                if (jwtTokenUtils.isTokenBlacklisted(authToken)) {
                    ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Token is blacklisted");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                    return;
                }
                String userName = jwtTokenUtils.getUserNameFromToken(authToken);
                if (null != userName) {
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                        if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            log.info("User '{}' authenticated successfully with JWT", userName);

                        }
                    } catch (UsernameNotFoundException e) {
                        log.warn("User not found: " + e.getMessage());
                        ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "User not found");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    } catch (BadCredentialsException e) {
                        log.warn("Invalid credentials: " + e.getMessage());
                        ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");

                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    } catch (Exception e) {
                        log.warn("Unexpected error during user authentication: " + e.getMessage());
                        ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");

                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                        return;
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired: " + e.getMessage());
            ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(),    "Token has expired");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (MalformedJwtException e) {
            log.warn("Token is malformed: " + e.getMessage());
            ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Token is malformed");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (SignatureException e) {
            log.warn("Token signature is invalid: " + e.getMessage());
            ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Token signature is invalid");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (JwtException e) {
            log.warn("JWT processing error: " + e.getMessage());
            ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "JWT processing error");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (Exception e) {
            log.warn("Unexpected error during authentication: " + e.getMessage());
            ResponseError errorResponse = new ResponseError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }

}
