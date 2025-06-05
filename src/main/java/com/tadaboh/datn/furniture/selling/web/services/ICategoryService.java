package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.CategoryRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;

import java.util.Collection;
import java.util.List;

public interface ICategoryService {
    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    void delete(Long id);
    CategoryResponse getById(Long id);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryBySlug(String slug);
    List<CategoryResponse> getCategoryByParentId(Long parentId);

    Collection<? extends Category> getAllSubCategories(Long categoryid);
    List<CategoryResponse> getAllCategoryResponses();
}
