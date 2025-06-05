package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.CategoryRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import com.tadaboh.datn.furniture.selling.web.repositories.CategoryRepository;
import com.tadaboh.datn.furniture.selling.web.services.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setSlug(GenerateCode.generateSlug(categoryRequest.getName()));
        if (categoryRequest.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new DataNotFoundException("Parent category not found"));
            category.setParent(parentCategory);
        }
        categoryRepository.save(category);
        return CategoryResponse.fromCategory(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());

        if (categoryRequest.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new DataNotFoundException("Parent category not found"));
            existingCategory.setParent(parentCategory);
        }

        categoryRepository.save(existingCategory);
        return CategoryResponse.fromCategory(existingCategory);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        categoryRepository.delete(existingCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryResponse> getCategoryByParentId(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug);
        if (category == null) {
            throw new DataNotFoundException("Category not found");
        }
        return CategoryResponse.fromCategory(category);
    }
    @Override
    public CategoryResponse getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new DataNotFoundException("Category not found");
        }
        return CategoryResponse.fromCategory(category.orElse(null));
    }

    @Override
    public Set<Category> getAllSubCategories(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("Category not found"));
        Set<Category> subCategories = new HashSet<>();
        Queue<Category> queue = new LinkedList<>();
        queue.add(existingCategory);

        while (!queue.isEmpty()) {
            Category current = queue.poll();
            List<Category> children = categoryRepository.findByParent(current);
            subCategories.addAll(children);
            queue.addAll(children);
        }
        return subCategories;
    }

    @Override
    public List<CategoryResponse> getAllCategoryResponses() {
        List<Category> parents = categoryRepository.findAllParentCategories();
        return parents.stream().map(CategoryResponse::fromCategory).toList();
    }

}
