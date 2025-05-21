package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse extends AuditableResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private List<SubCategoryResponse> subcategories;


    public static CategoryResponse fromCategory(Category category) {
        List<SubCategoryResponse> subResponses = null;

        if (category.getSubcategories() != null) {
            subResponses = category.getSubcategories().stream()
                    .map(SubCategoryResponse::from)
                    .toList();
        }

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .slug(category.getSlug())
                .subcategories(subResponses)
                .build();

        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());

        return categoryResponse;
    }

}
