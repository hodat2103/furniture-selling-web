package com.tadaboh.datn.furniture.selling.web.models.categories;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCategory {
    @EmbeddedId
    private PromotionCategoryId id;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "apply_to_subcategories", nullable = false)
    private Boolean applyToSubCategories;
}
