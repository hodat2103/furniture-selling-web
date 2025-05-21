package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PromotionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PromotionResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.categories.*;
import com.tadaboh.datn.furniture.selling.web.repositories.*;
import com.tadaboh.datn.furniture.selling.web.services.IPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService implements IPromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionCategoryRepository promotionCategoryRepository;
    private final PromotionConditionRepository promotionConditionRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @Override
    public Set<CategoryResponse> getAllApplicableCategories(Long promotionId) {
        Promotion existingPromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new DataNotFoundException("Promotion not found"));
        Set<Category> applicableCategories = new HashSet<>();

        for (PromotionCategory promotionCategory : existingPromotion.getPromotionCategories()) {
            Category category = promotionCategory.getCategory();
            applicableCategories.add(category);

            if (promotionCategory.getApplyToSubCategories()) {
                applicableCategories.addAll(categoryService.getAllSubCategories(category.getId()));
            }
        }
        return applicableCategories.stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toSet());
    }

    @Transactional
    public PromotionResponse create(PromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .code(GenerateCode.generatePromotionCode())
                .title(request.getTitle())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();

        Promotion savedPromotion = promotionRepository.save(promotion);

        PromotionCondition promotionCondition = PromotionCondition.builder()
                .promotion(savedPromotion)
                .discountValue(request.getDiscountValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        promotionConditionRepository.save(promotionCondition);

        for (Long categoryId : request.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new DataNotFoundException("Category not found"));

            PromotionCategory promotionCategory = new PromotionCategory(
                    new PromotionCategoryId(savedPromotion.getId(), category.getId()),
                    savedPromotion,
                    category,
                    request.getApplyToSubCategories()
            );
            promotionCategoryRepository.save(promotionCategory);
        }

        return mapToResponse(savedPromotion, promotionCondition);
    }

    @Transactional
    public PromotionResponse update(Long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Promotion not found"));


        promotion.setTitle(request.getTitle());
        promotion.setDescription(request.getDescription());
        promotion.setIsActive(request.getIsActive());

        PromotionCondition promotionCondition = promotionConditionRepository.findByPromotionId(id)
                .orElseGet(PromotionCondition::new);

        promotionCondition.setPromotion(promotion);
        promotionCondition.setDiscountValue(request.getDiscountValue());
        promotionCondition.setStartDate(request.getStartDate());
        promotionCondition.setEndDate(request.getEndDate());

        promotionConditionRepository.save(promotionCondition);
        promotionRepository.save(promotion);

        return mapToResponse(promotion, promotionCondition);
    }


    @Transactional
    public void deleteSoften(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setIsActive(false);
        promotionRepository.save(promotion);
    }

    @Transactional
    public void deleteStiffen(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new RuntimeException("Promotion not found");
        }
        promotionCategoryRepository.deleteByPromotionId(id);
        promotionConditionRepository.deleteByPromotionId(id);
        promotionRepository.deleteById(id);
    }


    public List<PromotionResponse> findAllActivePromotions() {
        return promotionRepository.findByIsActiveTrue()
                .stream()
                .map(promotion -> {
                    PromotionCondition condition = promotionConditionRepository.findByPromotionId(promotion.getId()).orElse(null);
                    return mapToResponse(promotion, condition);
                })
                .collect(Collectors.toList());
    }

    private PromotionResponse mapToResponse(Promotion promotion, PromotionCondition promotionCondition) {
        Set<Long> categoryIds = promotionCategoryRepository.findByCategoryId(promotion.getId())
                .stream()
                .map(pc -> pc.getCategory().getId())
                .collect(Collectors.toSet());

        return PromotionResponse.builder()
                .id(promotion.getId())
                .title(promotion.getTitle())
                .description(promotion.getDescription())
                .isActive(promotion.getIsActive())
                .categoryIds(categoryIds)
                .discountValue(promotionCondition != null ? promotionCondition.getDiscountValue() : null)
                .startDate(promotionCondition != null ? String.valueOf(promotionCondition.getStartDate()) : null)
                .endDate(promotionCondition != null ? String.valueOf(promotionCondition.getEndDate()) : null)
                .createdAt(String.valueOf(promotion.getCreatedAt()))
                .updatedAt(String.valueOf(promotion.getUpdatedAt()))
                .build();
    }
}
