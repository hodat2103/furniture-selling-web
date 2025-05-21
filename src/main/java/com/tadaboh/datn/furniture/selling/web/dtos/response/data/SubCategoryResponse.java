package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;

    public static SubCategoryResponse from(Category category) {
        return SubCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }
}
