package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PromotionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PromotionResponse;
import com.tadaboh.datn.furniture.selling.web.models.categories.Promotion;

import java.util.List;
import java.util.Set;

public interface IPromotionService {
    Set<CategoryResponse> getAllApplicableCategories(Long promotionId);
    PromotionResponse create(PromotionRequest request);
    PromotionResponse update(Long id, PromotionRequest request);
    List<PromotionResponse> findAllActivePromotions();
    void deleteStiffen(Long id);
    void deleteSoften(Long id);
    
}
