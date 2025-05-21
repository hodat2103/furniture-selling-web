package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.categories.PromotionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionCategoryRepository extends JpaRepository<PromotionCategory, Long> {
    List<PromotionCategory> findByCategoryId(Long categoryId);
    void deleteByPromotionId(Long promotionId);
}
