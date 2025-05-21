package com.tadaboh.datn.furniture.selling.web.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

}
