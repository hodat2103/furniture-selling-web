package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse extends AuditableResponse {
    private Long id;
    private String email;
    private String fullname;
    private String phone;
    private String address;
    private String role;
    @JsonProperty("image_url")
    private String imageUrl;
    private Boolean active;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .phone(user.getPhone())
                .role(user.getRole().getName())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .active(user.getIsActive())
                .build();

        userResponse.setId(user.getId());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }
}
