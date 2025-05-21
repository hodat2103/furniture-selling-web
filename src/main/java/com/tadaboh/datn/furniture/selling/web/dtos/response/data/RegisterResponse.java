package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.tadaboh.datn.furniture.selling.web.models.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private CharSequence password;
    private Boolean isActive;

    public static RegisterResponse fromRegisterResponse(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .fullName(user.getFullname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .isActive(user.getIsActive())
                .build();
    }
}
