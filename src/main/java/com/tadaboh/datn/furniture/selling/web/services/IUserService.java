package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.LoginRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.RegisterRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.UserRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.LoginResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RegisterResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.UserResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IUserService {
    public LoginResponse login(LoginRequest loginRequest);
    public void logout(String token);
    public RegisterResponse register(RegisterRequest registerRequest);
    public UserResponse updateUser(UserRequest userRequest, MultipartFile multipartFile) throws IOException;
    public UserResponse getUserByEmail(String email);
    void deleteUser(String email);
    public void verifyUser(String email, String code) ;
    public String refreshToken (String refreshToken);
    public void changePassword(String email, String password);
    public void resetPassword(String email);
    public void lockUser(String email);
    public void unlockUser(String email);
    public void changeEmail(String email);
    public void changePhone(String email, String phone);
    void resendVerificationCode(String email);
}
