package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private String name;
    private String description;

    @JsonProperty("permission_ids")
    private Set<Long> permissionIds;
}
