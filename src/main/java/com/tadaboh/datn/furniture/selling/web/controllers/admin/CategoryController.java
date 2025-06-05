package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.request.CategoryRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseError;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.services.impl.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}categories")
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.CREATED,
                "Create category successfully",
                categoryResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.update(id, categoryRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Update category successfully",
                categoryResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Delete category successfully",
                null
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> get(@PathVariable String slug) {
        CategoryResponse categoryResponse = categoryService.getCategoryBySlug(slug);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Get category successfully",
                categoryResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/{category_id}")
    public ResponseEntity<?> getById(@PathVariable(name = "category_id") Long categoryId) {
        CategoryResponse categoryResponse = categoryService.getById(categoryId);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Get category successfully",
                categoryResponse.getName()
        );
        return ResponseEntity.ok(responseSuccess);
    }


    @GetMapping("/parent/{parent_id}")
    public ResponseEntity<?> getByParentId(@PathVariable(name = "parent_id") Long parentId) {
        List<CategoryResponse> categoryResponse = categoryService.getCategoryByParentId(parentId);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.OK,
                "Get category successfully",
                categoryResponse
        );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try{
            List<CategoryResponse> categoryResponse = categoryService.getAllCategoryResponses();
            ResponseSuccess responseSuccess = new ResponseSuccess(
                    HttpStatus.OK,
                    "Get all categories successfully",
                    categoryResponse
            );
            return ResponseEntity.ok(responseSuccess);
        }catch (Exception e){
            ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseError);
        }

    }

}
