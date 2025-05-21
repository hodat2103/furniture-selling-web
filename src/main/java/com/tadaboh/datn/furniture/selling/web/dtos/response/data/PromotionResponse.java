package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.categories.Promotion;
import com.tadaboh.datn.furniture.selling.web.validator.CustomDateValidator;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponse {
    private Long id;
    private String code;
    private String title;
    private String description;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("category_ids")
    private Set<Long> categoryIds;

    @JsonProperty("discount_value")
    private BigDecimal discountValue;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

//    public static PromotionResponse fromPromotion(Promotion promotion) {
//        return PromotionResponse.builder()
//                .id(promotion.getId())
//                .code(promotion.getCode())
//                .title(promotion.getTitle())
//                .description(promotion.getDescription())
//                .isActive(promotion.getIsActive())
//                .categoryIds(promotion.getPromotionCategories().stream().map(pc -> pc.getCategory().getId()).collect(Collectors.toSet()))
//                .createdAt(CustomDateValidator.formatLocalDateTime(promotion.getCreatedAt()))
//                .updatedAt(CustomDateValidator.formatLocalDateTime(promotion.getUpdatedAt()))
//                .build();
//    }
}
