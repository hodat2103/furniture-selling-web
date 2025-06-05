package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.FeedbackRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.FeedbackResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.models.products.Product;
import com.tadaboh.datn.furniture.selling.web.models.users.Feedback;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import com.tadaboh.datn.furniture.selling.web.repositories.FeedbackRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.ProductRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.UserRepository;
import com.tadaboh.datn.furniture.selling.web.services.IFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedbackService implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public FeedbackResponse saveFeedback(FeedbackRequest feedbackRequest) {
        Product product = productRepository.findById(feedbackRequest.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
        User user = userRepository.findById(feedbackRequest.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));

        Feedback feedback = Feedback.builder()
                .product(product)
                .user(user)
                .comment(feedbackRequest.getComment())
                .ratingValue(feedbackRequest.getRating())
                .isDeleted(false)
                .build();

        Feedback saved = feedbackRepository.save(feedback);
        return FeedbackResponse.fromFeedback(saved);
    }

    @Override
    public void disableFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        feedback.setIsDeleted(true);
    }

    @Override
    public Page<FeedbackResponse> getFeedbacks(Long productId, Long userId, String comment, Pageable pageable) {

        Page<Feedback> feedbacks = feedbackRepository.findByUserIdAndProductIdAndCommentContaining(productId, userId, comment,pageable);
        return feedbacks.map(feedback -> FeedbackResponse.builder()
                .id(feedback.getId())
                .comment(feedback.getComment())
                .rating(feedback.getRatingValue())
                .userId(feedback.getUser().getFullname())
                .productId(feedback.getProduct().getSlug())
                .build());
    }
}
