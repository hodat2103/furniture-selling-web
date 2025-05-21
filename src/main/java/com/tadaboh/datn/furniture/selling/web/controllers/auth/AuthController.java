package com.tadaboh.datn.furniture.selling.web.controllers.auth;

import com.tadaboh.datn.furniture.selling.web.configurations.JwtTokenUtils;
import com.tadaboh.datn.furniture.selling.web.dtos.request.LoginRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RegisterRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.LoginResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RegisterResponse;
import com.tadaboh.datn.furniture.selling.web.services.IUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}auth")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    /**
     * Đăng ký người dùng mới vào hệ thống.
     *
     * @param request chứa các thông tin người dùng cần đăng ký như tên, email, số điện thoại, mật khẩu.
     * @return ResponseEntity chứa kết quả đăng ký và mã trạng thái.
     */

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid registration details or user already exists.",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse registerResponse = userService.register(request);
        return new ResponseEntity<>(registerResponse, (registerResponse != null) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }
    /**
     * Xác minh email người dùng thông qua mã xác minh.
     *
     * @param map chứa email và mã xác minh người dùng nhập vào.
     * @return ResponseEntity với mã trạng thái xác nhận nếu mã xác minh đúng, hoặc lỗi nếu sai.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully verified.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(example = "{ \"email\": \"user@example.com\", \"code\": \"123456\" }"))}),
            @ApiResponse(responseCode = "400", description = "Invalid verification code or email.",
                    content = @Content)
    })
    @PostMapping("/resend-verify-code")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> map)  {
        String email = map.get("email");
        String code = map.get("code");
        if (email != null && code != null) {
            userService.verifyUser(email, code);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Verify successful",
                    "status", HttpStatus.OK
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "message", "Verify error",
                "status", HttpStatus.BAD_REQUEST
        ));
    }
    @PostMapping("/verify")
    public ResponseEntity<?> resendVerifyCode(@RequestBody Map<String, String> map)  {
        String email = map.get("email");
        if (email != null) {
            userService.resendVerificationCode(email);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Resend successful",
                    "status", HttpStatus.OK
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "message", "Verify error",
                "status", HttpStatus.BAD_REQUEST
        ));
    }
    /**
     * Đăng nhập người dùng và trả về một JWT token.
     *
     * @param request chứa email và mật khẩu của người dùng cần đăng nhập.
     * @return ResponseEntity chứa JWT token nếu đăng nhập thành công, hoặc lỗi nếu thông tin không chính xác.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully verified.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(example = "{ \"email\": \"user@example.com\", \"code\": \"123456\" }"))}),
            @ApiResponse(responseCode = "400", description = "Invalid verification code or email.",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse userInfo = userService.login(request);
        return new ResponseEntity<>(userInfo, userInfo != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest request) {
        String token = jwtTokenUtils.getToken(request);
        if(token != null) {
            try {
                userService.logout(token);
                return ResponseEntity.ok().body(Map.of(
                        "message", "Logout successful",
                        "status", HttpServletResponse.SC_OK
                ));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(Map.of(
                        "message", "Logout error",
                        "status", HttpServletResponse.SC_BAD_REQUEST
                ));
            }
        }
        return ResponseEntity.badRequest().body(Map.of(
                "message", "No token found",
                "status", HttpStatus.BAD_REQUEST
        ));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken (@RequestHeader("Refresh-Token") String refreshToken) {
        String newToken = userService.refreshToken(refreshToken);
        if (newToken != null) {
            return ResponseEntity.ok().body(Map.of(
                    "token", newToken,
                    "status", HttpStatus.OK
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "message", "No token found",
                "status", HttpStatus.BAD_REQUEST
        ));    }
}
