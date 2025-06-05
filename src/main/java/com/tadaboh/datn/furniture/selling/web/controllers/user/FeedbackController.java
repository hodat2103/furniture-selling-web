package com.tadaboh.datn.furniture.selling.web.controllers.user;

import com.tadaboh.datn.furniture.selling.web.dtos.request.FeedbackRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.FeedbackResponse;
import com.tadaboh.datn.furniture.selling.web.services.IFeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}feedbacks")
@Tag(name = "Feedback")
public class FeedbackController {
    private final IFeedbackService feedbackService;
    @PostMapping()
    public ResponseEntity<ResponseSuccess> saveFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        FeedbackResponse feedbackResponse = feedbackService.saveFeedback(feedbackRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Feedback created successfully", feedbackResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{feedback_id}")
    public ResponseEntity<ResponseSuccess> updateFeedback(@PathVariable(name = "feedback_id") Long feedbackId
                                                          ) {
        feedbackService.disableFeedback(feedbackId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Feedback updated successfully");
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("")
    public ResponseEntity<ResponseSuccess> getFeedbacks(
            @RequestParam(name = "product_id") Long productId,
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "comment", required = false) String comment,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
       Page<FeedbackResponse> feedbackResponses = feedbackService.getFeedbacks(productId, userId, comment, PageRequest.of(page, size));
       ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Feedbacks retrieved successfully", feedbackResponses);
       return ResponseEntity.ok(responseSuccess);
    }
}
