package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIdIn(Set<Long> categoryIds);
    List<Category> findByParent(Category parent);
    List<Category> findByParentId(Long parentId);
    Category findBySlug(String slug);
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllParentCategories();
}
