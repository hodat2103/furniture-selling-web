package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.users.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f WHERE (:userId IS NULL OR f.user.id = :userId) AND (:productId IS NULL OR f.product.id = :productId) AND (:comment IS NULL OR f.comment LIKE %:comment%)")
    Page<Feedback> findByUserIdAndProductIdAndCommentContaining(Long userId, Long productId, String comment, Pageable pageable);
}
