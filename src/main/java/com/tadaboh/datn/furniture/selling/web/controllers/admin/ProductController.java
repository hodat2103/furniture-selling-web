package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadaboh.datn.furniture.selling.web.dtos.request.ProductRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import com.tadaboh.datn.furniture.selling.web.services.IProductItemService;
import com.tadaboh.datn.furniture.selling.web.services.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final IProductService productService;
    private final IProductItemService productItemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseSuccess> create (@RequestPart(value = "productRequest") String productRequestJson,
                                                   @RequestPart(value = "multipartFiles") List<MultipartFile> multipartFiles ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
        ProductDetailResponse productDetailResponse = productService.create(productRequest,multipartFiles);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "",productDetailResponse );
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping(value = "/{product_id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseSuccess> update (@PathVariable(name = "product_id") Long productId ,
                                                   @RequestPart(value = "productRequest") String productRequestJson,
                                                   @RequestPart(value = "multipartFiles") List<MultipartFile> multipartFiles) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
        ProductDetailResponse  productDetailResponse = productService.update(productId, productRequest, multipartFiles);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productDetailResponse );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/categories/{category_id}")
    public ResponseEntity<ResponseSuccess> getByCategoryId(@PathVariable(name = "category_id") Long categoryId,
                                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @RequestParam(defaultValue = "12") @Min(1) @Max(15) int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productResponses = productService.getByCategoryId(categoryId,pageable);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productResponses );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/{product_id}")
    public ResponseEntity<ResponseSuccess> getById(@PathVariable(name = "product_id") Long productId){
        ProductDetailResponse productDetailResponse = productService.getById(productId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productDetailResponse );
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ResponseSuccess> getByCode(@PathVariable String code) throws Exception {
        ProductResponse productResponse = productService.getByCode(code);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productResponse );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ResponseSuccess> getBySlug(@PathVariable String slug) throws Exception {
        ProductResponse productResponse = productService.getBySlug(slug);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productResponse );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/stock/{product_id}")
    public ResponseEntity<ResponseSuccess> getStockByProductId(@PathVariable(name = "product_id") Long productId){
        int stock = productItemService.getStockByProductId(productId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Get stock of product" ,stock);
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/tag")
    public ResponseEntity<ResponseSuccess> getById(@RequestParam String tag,
                                                   @RequestParam(defaultValue = "0") @Min(0) int page,
                                                   @RequestParam(defaultValue = "9") @Min(1) @Max(15) int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productResponses = productService.getByTag(tag,pageable);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "",productResponses );
        return ResponseEntity.ok(responseSuccess);
    }
     @GetMapping("")
     public ResponseEntity<ResponseSuccess> filterProduct(@RequestParam(name = "", required = false) String slug,
                                                  @RequestParam(name = "category_id", required = false) Long categoryId,
                                                  @RequestParam(name = "supplier_id", required = false) Long supplierId,
                                                  @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                                  @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
                                                  @RequestParam(defaultValue = "0") @Min(0) int page,
                                                  @RequestParam(defaultValue = "9") @Min(1) @Max(15) int size,
                                                  @RequestParam(defaultValue = "desc") String sort) {
         PageRequest pageRequest = PageRequest.of(page, size, sort.equalsIgnoreCase("asc") ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending());
         Page<ProductResponse> productResponses = productService.filterProduct(slug, categoryId, supplierId, minPrice, maxPrice, pageRequest);
         int totalPage = productResponses.getTotalPages();
         if (totalPage == 0) {
             return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
         }
         ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "", productResponses);
         return ResponseEntity.ok(responseSuccess);
     }

    @GetMapping("/image-url-list/{product_id}")
    public ResponseEntity<ResponseSuccess> getImageUrlFromPublicId(@PathVariable(name = "product_id")  Long productId) throws Exception {
        List<String> imageUrlList = productService.getImageUrlFromPublicId(productId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "",imageUrlList );
        return ResponseEntity.ok(responseSuccess);
    }


    @DeleteMapping("/delete-soften/{id}")
    public ResponseEntity<ResponseSuccess> deleteSoften(@PathVariable(name = "id") Long productId){
        productService.deleteSoften(productId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Deleted soften",null );
        return ResponseEntity.ok(responseSuccess);
    }
    @DeleteMapping("/delete-stiffen/{id}")
    public ResponseEntity<ResponseSuccess> deleteStiffen(@PathVariable(name = "id") Long productId){
        productService.deleteStiffen(productId);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Deleted stiffen",null );
        return ResponseEntity.ok(responseSuccess);
    }

}
