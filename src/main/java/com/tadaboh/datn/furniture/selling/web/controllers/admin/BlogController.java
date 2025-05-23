package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadaboh.datn.furniture.selling.web.dtos.request.BlogRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.ProductRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.BlogResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.CategoryResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import com.tadaboh.datn.furniture.selling.web.services.IBlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}blogs")
@Tag(name = "Blog", description = "Blog API")
public class BlogController {
    private final IBlogService blogService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseSuccess> create (@RequestPart(value = "blogRequest") String blogRequestJson,
                                                   @RequestPart(value = "multipartFile") MultipartFile multipartFile ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogRequest blogRequest = objectMapper.readValue(blogRequestJson, BlogRequest.class);
        BlogResponse blogResponse = blogService.create(blogRequest,multipartFile);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "",blogResponse );
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping(value = "/{blog_id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseSuccess> update (@PathVariable(name = "blog_id") Long blogId ,
                                                   @RequestPart(value = "blogRequest") String blogRequestJson,
                                                   @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogRequest blogRequest = objectMapper.readValue(blogRequestJson, BlogRequest.class);
        BlogResponse  blogResponse = blogService.update(blogId, blogRequest, multipartFile);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",blogResponse );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping()
    public ResponseEntity<ResponseSuccess> getAll(@RequestParam(required = false) String slug,
                                                  @RequestParam(name = "employee_id", required = false) @Min(0) Long employeeId,
                                                  @RequestParam(defaultValue = "0") @Min(0) int page,
                                                  @RequestParam(defaultValue = "9") @Min(1) @Max(15) int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BlogResponse> blogResponses = blogService.filterBlog(slug,employeeId,pageRequest);
        int totalPage = blogResponses.getTotalPages();
        if(totalPage == 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",blogResponses );
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping
    public ResponseEntity<ResponseSuccess> deleteSoften(@RequestParam(name = "blog_id") Long blogId){
        blogService.deleteSoften(blogId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Delete blog successfully",null );
        return ResponseEntity.ok(responseSuccess);
    }

    @DeleteMapping("/hard")
    public ResponseEntity<ResponseSuccess> deleteHard(@RequestParam(name = "blog_id") Long blogId){
        blogService.deleteHard(blogId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Delete blog successfully",null );
        return ResponseEntity.ok(responseSuccess);
    }
}
