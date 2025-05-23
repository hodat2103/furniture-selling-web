package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.users.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findBySlug(String slug);
    Blog findByEmployeeId(Long employeeId);
    @Query("SELECT b FROM Blog b WHERE " +
            "(:slug IS NULL OR :slug = '' OR b.slug = :slug) AND " +
            "(:employeeId IS NULL OR b.employee.id = :employeeId)")
    Page<Blog> filterBlog(String slug, Long employeeId, PageRequest pageRequest);
}
