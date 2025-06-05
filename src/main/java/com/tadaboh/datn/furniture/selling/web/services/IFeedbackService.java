package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.FeedbackRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.FeedbackResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IFeedbackService {
    FeedbackResponse saveFeedback(FeedbackRequest feedbackRequest);
    void disableFeedback(Long feedbackId);
    Page<FeedbackResponse> getFeedbacks(Long productId, Long userId, String comment, Pageable pageable);
}
