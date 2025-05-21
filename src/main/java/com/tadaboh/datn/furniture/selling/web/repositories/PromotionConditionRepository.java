package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.categories.PromotionCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PromotionConditionRepository extends JpaRepository<PromotionCondition, Long> {
    Optional<PromotionCondition> findByPromotionId(Long promotionId);
    void deleteByPromotionId(Long promotionId);
}

