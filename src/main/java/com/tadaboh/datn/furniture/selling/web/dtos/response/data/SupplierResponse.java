package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.products.Supplier;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("is_active")
    private Boolean isActive;

    public static SupplierResponse fromSupplier(Supplier supplier){
        SupplierResponse supplierResponse = SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .description(supplier.getDescription())
                .isActive(supplier.getIsActive())
                .build();
        supplierResponse.setId(supplier.getId());

        return supplierResponse;
    }
}
