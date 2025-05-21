package com.tadaboh.datn.furniture.selling.web.models.categories;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PromotionCategoryId implements Serializable {
    private Long promotionId;
    private Long categoryId;
}

