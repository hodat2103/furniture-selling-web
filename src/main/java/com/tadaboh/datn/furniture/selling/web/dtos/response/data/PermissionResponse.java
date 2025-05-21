package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.users.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private Long id;

    private String name;

    @JsonProperty("end_point")
    private String endPoint;

    @JsonProperty("http_method")
    private String httpMethod;

    public static PermissionResponse fromPermission(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .endPoint(permission.getEndPoint())
                .httpMethod(permission.getHttpMethod())
                .build();
    }
}
