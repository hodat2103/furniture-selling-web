package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.Feedback;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse extends AuditableResponse {
    private Long id;
    private String comment;
    private Integer rating;
    private String productId;
    private String userId;

    public static FeedbackResponse fromFeedback(Feedback feedback) {
        FeedbackResponse feedbackResponse =  FeedbackResponse.builder()
                .id(feedback.getId())
                .comment(feedback.getComment())
                .rating(feedback.getRatingValue())
                .productId(feedback.getProduct().getSlug())
                .userId(feedback.getUser().getFullname())
                .build();
        feedbackResponse.setCreatedAt(feedback.getCreatedAt());
        feedbackResponse.setUpdatedAt(feedback.getUpdatedAt());
        return feedbackResponse;

    }
}
