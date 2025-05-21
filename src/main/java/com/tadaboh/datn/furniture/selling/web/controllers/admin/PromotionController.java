package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PromotionRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PromotionResponse;
import com.tadaboh.datn.furniture.selling.web.services.IPromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}promotions")
public class PromotionController {
    private final IPromotionService promotionService;

    @GetMapping("/{id}/categories")
    public ResponseEntity<ResponseSuccess> getAllApplicableCategories(@PathVariable Long id) {
        Set<CategoryResponse> categories = promotionService.getAllApplicableCategories(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "", categories);
        return ResponseEntity.ok(responseSuccess);
    }

    @PostMapping
    public ResponseEntity<ResponseSuccess> create(@RequestBody PromotionRequest request) {
        log.info("Datetime start: {}", request.getStartDate());
        log.info("Datetime end: {}", request.getEndDate());
        PromotionResponse promotion = promotionService.create(request);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "", promotion);
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseSuccess> update(@PathVariable Long id, @RequestBody PromotionRequest request) {
        PromotionResponse promotion = promotionService.update(id, request);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "", promotion);
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseSuccess> findAllActivePromotions() {
        List<PromotionResponse> promotions = promotionService.findAllActivePromotions();
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "", promotions);
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<ResponseSuccess> deleteSoften(@PathVariable Long id) {
        promotionService.deleteSoften(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Deleted soften", null);
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ResponseSuccess> deleteStiffen(@PathVariable Long id) {
        promotionService.deleteStiffen(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Deleted stiffen", null);
        return ResponseEntity.ok(responseSuccess);
    }
}
