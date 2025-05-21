package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.enums.HttpMethodEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "End point is required")
    @JsonProperty("end_point")
    private String endPoint;

    @NotBlank(message = "Http method is required")
    @JsonProperty("http_method")
    private String httpMethod;
}
