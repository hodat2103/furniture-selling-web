package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.configurations.JwtTokenUtils;
import com.tadaboh.datn.furniture.selling.web.dtos.request.LoginRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RegisterRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.UserRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.LoginResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RegisterResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.UserResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.AuthenticationFailedException;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.exceptions.UnauthorizedException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.users.Role;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import com.tadaboh.datn.furniture.selling.web.repositories.RoleRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.UserRepository;
import com.tadaboh.datn.furniture.selling.web.services.IUserService;
import com.tadaboh.datn.furniture.selling.web.utils.ConstantKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements  IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;
    private final JwtTokenUtils jwtTokenUtils;
    private static final LocalDateTime  CODE_EXPIRATION = LocalDateTime.now().plusMinutes(5);
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword());
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);
            if (authenticationResponse.isAuthenticated()) {
                User user = (User) authenticationResponse.getPrincipal();
                // nếu người dùng chưa xác thực, mã hết hạn thì gửi lại
                if (!user.isEnabled() && user.getVerificationCode() == null && user.getCodeExpiration() == null) {
                    String code = GenerateCode.generateVerificationCode();
                    user.setVerificationCode(code);
                    user.setCodeExpiration(LocalDateTime.now().plusMinutes(5));
                    userRepository.save(user);
                    emailService.sendVerificationCode(user);
                }
                String token = jwtTokenUtils.generateToken(email);
                LoginResponse loginResponse = LoginResponse.builder()
                        .id(user.getId())
                        .fullname(user.getFullname())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhone())
                        .role(user.getRole().getName())
                        .isActive(user.isEnabled())
                        .token(token)
                        .build();
                return loginResponse;
            }
        } catch (BadCredentialsException e) {
            log.warn("Xác thực thất bại cho tài khoản: {}", email);
            throw new AuthenticationFailedException("Tên người dùng hoặc mật khẩu không chính xác");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.error("Xác thực thất bại không rõ lý do cho tài khoản: {}", email);
        throw new AuthenticationFailedException("Đăng nhập không thành công");
    }

    @Override
    public void logout(String token) {
        if (token != null) {
            try {
                jwtTokenUtils.logout(token);
                log.info("logout success !");
            } catch (Exception e) {
                throw new ServerErrorException(e.getMessage(), e.getCause());
            }
        }
    }
    public Role getUserRole() {
        Role role = roleRepository.findByName(ConstantKey.USER);
        return role != null ? role : roleRepository.save(Role.builder().name(ConstantKey.USER).build());
    }
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Optional<User> userExisting = userRepository.findByEmail(registerRequest.getEmail());
        if (userExisting.isPresent()) {
            log.warn("Tài khoản đã tồn tại với email: {}", registerRequest.getEmail());
            throw new UnauthorizedException("Tài khoản đã tồn tại");
        }
        try {
            String code = GenerateCode.generateVerificationCode();
            log.info("Generated verification code: {}", code);
            User user = User.builder()
                    .fullname(registerRequest.getFullname())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phone(registerRequest.getPhone())
                    .verificationCode(code)
                    .codeExpiration(CODE_EXPIRATION)
                    .role(getUserRole())
                    .build();
            userRepository.save(user);
            emailService.sendVerificationCode(user);
            log.info("Sent verification code to email: {}", user.getEmail());
            return RegisterResponse.fromRegisterResponse(user);
        }catch (Exception e) {
            log.error("Error creating account");
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, MultipartFile avatarFile) throws IOException {
        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if (optionalUser.isEmpty()) {
            log.warn("Tài khoản không tồn tại với email: {}", userRequest.getEmail());
            throw new UnauthorizedException("Tài khoản không tồn tại");
        }
        User user = optionalUser.get();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(avatarFile, null , "AVATAR");
            user.setImageUrl(imageUrl);
        }
        user.setFullname(userRequest.getFullname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        return UserResponse.fromUser(userRepository.save(user));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<User> user =  userRepository.findByEmail(email);
        log.info("user: " + user.get());
        if (user.isPresent()) {
            throw new DataNotFoundException("Tài khoản không tồn tại với email: " + email);
        }
        return UserResponse.fromUser(user.get());
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void verifyUser(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }

        if (user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification code has expired.");
        }
        if (user.getIsActive() != null) {
            log.info("Tài khoản với email {} đã được kích hoạt trước đó", user.getFullname());
            return;
        }
        user.setIsActive(true);

        user.setVerificationCode(null);
        user.setCodeExpiration(null);
        userRepository.save(user); // Chỉ save một lần
        log.info("Tài khoản với email {} đã được kích hoạt thành công",  user.getFullname());
    }

    @Override
    public String refreshToken(String refreshToken) {
        if(refreshToken != null) {
            try {
                String newToken = jwtTokenUtils.refreshToken(refreshToken);
                log.info("Refresh token success !");
                return newToken;
            } catch (Exception e) {
                log.error("Refresh token err");
                throw new ServerErrorException(e.getMessage(), e.getCause());
            }
        }
        return  null;    }

    @Override
    public void changePassword(String email, String password) {

    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public void lockUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));

        if (!user.get().getIsActive()) {
            log.info("Tài khoản với email {} đã bị khóa trước đó", user.get().getEmail());
            return;
        }
        user.get().setIsActive(false);
        userRepository.save(user.get());
        log.info("Tài khoản với email {} đã bị khóa", user.get().getEmail());

    }

    @Override
    public void unlockUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));

        if (user.get().getIsActive()) {
            log.info("Tài khoản với email {} đang hoạt động bình thường", user.get().getEmail());
            return;
        }
        user.get().setIsActive(true);
        userRepository.save(user.get());
        log.info("Tài khoản với email {} đã được mở khóa", user.get().getEmail());
    }

    @Override
    public void changeEmail(String email) {

    }

    @Override
    public void changePhone(String email, String phone) {

    }

    @Override
    public void resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String code = emailService.sendVerificationCode(user);
        user.setVerificationCode(code);
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

    }

}
