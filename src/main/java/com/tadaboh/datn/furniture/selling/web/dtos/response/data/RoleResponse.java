package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import com.tadaboh.datn.furniture.selling.web.models.users.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse extends AuditableResponse {
    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

    public static RoleResponse fromRole(Role role) {
        RoleResponse roleResponse = RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .build();

        roleResponse.setCreatedAt(role.getCreatedAt());
        roleResponse.setUpdatedAt(role.getUpdatedAt());

        return roleResponse;
    }
}
