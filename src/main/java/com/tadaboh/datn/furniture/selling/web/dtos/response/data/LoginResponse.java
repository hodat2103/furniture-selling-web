package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String fullname;
    private String phoneNumber;
    private String email;
    private String role;
    private Set<Object> authorityList;
    private String token;
    private Boolean isActive;
}
