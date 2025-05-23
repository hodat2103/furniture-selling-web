package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.ProductRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import com.tadaboh.datn.furniture.selling.web.models.products.*;
import com.tadaboh.datn.furniture.selling.web.repositories.*;
import com.tadaboh.datn.furniture.selling.web.services.IProductService;
import com.tadaboh.datn.furniture.selling.web.utils.ConstantKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final MaterialRepository materialRepository;

    @Override
    public ProductDetailResponse create(ProductRequest productRequest,List<MultipartFile>  imageFiles) throws IOException {
        Category existingCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        Supplier existingSupplier = supplierRepository.findById(productRequest.getSupplierId()).orElseThrow(
                () -> new DataNotFoundException("Supplier not found")
        );

        List<Material> materials = new ArrayList<>();

        for (Long materialId : productRequest.getMaterialIds()) {
            Material material = materialRepository.findById(materialId).orElseThrow(
                    () -> new DataNotFoundException("Material not found")
            );
            materials.add(material);
        }
        Product product = new Product();
        ProductItem productItem = new ProductItem();

        product.setCode(GenerateCode.generateProductCode());
        product.setSlug(GenerateCode.generateSlug(productRequest.getName()));
        product.setName(productRequest.getName());
        product.setTag(productRequest.getTag().name());
        product.setPrice(productRequest.getPrice());
        product.setSupplier(existingSupplier);
        product.setCategory(existingCategory);
        product.setIsActive(true);

        Product insertProduct = productRepository.save(product);

        productItem.setDescription(productRequest.getDescription());
        productItem.setMaterials(materials);
        productItem.setSize(productRequest.getSize());
        productItem.setStock(productRequest.getStock());
        productItem.setWarranty(productRequest.getWarranty());
        productItem.setProduct(insertProduct);

        ProductItem insertProductItem = productItemRepository.save(productItem);

        List<ProductImage> productImages = cloudinaryService.uploadMultipleImages(insertProductItem, imageFiles);
        List<ProductImage> insertProductImages = productImageRepository.saveAll(productImages);



        return ProductDetailResponse.fromProductDetail(insertProduct, insertProductItem, insertProductImages);
    }

    @Transactional
    @Override
    public ProductDetailResponse update(Long id, ProductRequest productRequest, List<MultipartFile> imageFiles) throws IOException {

        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Product not found")
        );
        ProductItem existingProductItem = productItemRepository.findByProductId(existingProduct.getId());
        if (existingProductItem == null) {
            throw new DataNotFoundException("Product item not found");
        }

        Category existingCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        Supplier existingSupplier = supplierRepository.findById(productRequest.getSupplierId()).orElseThrow(
                () -> new DataNotFoundException("Supplier not found")
        );

        existingProduct.setCode(GenerateCode.generateProductCode());
        existingProduct.setSlug(GenerateCode.generateSlug(productRequest.getName()));
        existingProduct.setName(productRequest.getName());
        existingProduct.setTag(productRequest.getTag().name());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setSupplier(existingSupplier);
        existingProduct.setCategory(existingCategory);

        Product updatedProduct = productRepository.save(existingProduct);

        existingProductItem.setDescription(productRequest.getDescription());
        existingProductItem.setSize(productRequest.getSize());
        existingProductItem.setStock(productRequest.getStock());
        existingProductItem.setWarranty(productRequest.getWarranty());

        List<Material> materials = productRequest.getMaterialIds().stream()
                .map(materialId -> materialRepository.findById(materialId)
                        .orElseThrow(() -> new DataNotFoundException("Material not found")))
                .collect(Collectors.toList());

        existingProductItem.setMaterials(materials);
        existingProductItem.setProduct(updatedProduct);

        ProductItem updatedProductItem = productItemRepository.save(existingProductItem);

        deleteOldImages(updatedProductItem.getId());
        productImageRepository.deleteAll(productImageRepository.findByProductItemId(updatedProductItem.getId()));
        log.info("Deleted old images with product item id: {}", updatedProductItem.getId());

        List<ProductImage> productImages = new ArrayList<>();
        // Update product images by color
        if (imageFiles != null && !imageFiles.isEmpty()) {
           productImages = cloudinaryService.uploadMultipleImages(updatedProductItem, imageFiles);

        }
        log.info("Uploaded product successfully !");
        return ProductDetailResponse.fromProductDetail(updatedProduct, updatedProductItem, productImages);
    }
    private void deleteOldImages(Long productItemId) {
        // Delete all old images associated with the product
        List<ProductImage> oldImages = productImageRepository.findByProductItemId(productItemId);
        if (oldImages != null && !oldImages.isEmpty()) {
            // Delete images from Cloudinary or file system if needed
            for (ProductImage image : oldImages) {
                // Assuming you have a method to delete image from Cloudinary or filesystem
                cloudinaryService.deleteFile(image.getImageUrl()); // Or file system delete logic
            }
        }
    }


    @Override
    public ProductDetailResponse getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Product not found")
        );
        ProductItem productItem = productItemRepository.findByProductId(product.getId());
        if (productItem == null) {
            throw new DataNotFoundException("Product item not found");
        }
        List<ProductImage> productImages = productImageRepository.findByProductItemId(productItem.getId());
        if (productImages.isEmpty()) {
            throw new DataNotFoundException("Product image not found");
        }
        return ProductDetailResponse.fromProductDetail(product, productItem, productImages);
    }

    @Override
    public ProductResponse getBySlug(String slug) {
        if (slug == null) {
            throw new DataNotFoundException("Don't empty slug");
        }
        Product product = productRepository.findBySlug(slug);
        if (product == null) {
            throw new DataNotFoundException("Product not found");
        }
        ProductItem productItem = productItemRepository.findByProductId(product.getId());
        List<ProductImage> productImages = productImageRepository.findByProductItemIdAndImageType(productItem.getId());
        return ProductResponse.fromProduct(product, productImages);
    }

    @Override
    public ProductResponse getByCode(String code) {
        if (code == null) {
            throw new DataNotFoundException("Can't empty code");
        }
        Product product = productRepository.findByCode(code);
        if (product == null) {
            throw new DataNotFoundException("Product not found");
        }
        ProductItem productItem = productItemRepository.findByProductId(product.getId());
        List<ProductImage> productImages = productImageRepository.findByProductItemIdAndImageType(productItem.getId());
        return ProductResponse.fromProduct(product, productImages);
    }

    @Override
    public Page<ProductResponse> getByCategoryId(Long categoryId, Pageable pageable) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        List<Product> products = productRepository.findByCategoryId(existingCategory.getId());
        Page<ProductResponse> productResponses = new PageImpl<>(new ArrayList<>(), pageable, products.size());
        for (Product product : products) {
            ProductItem productItem = productItemRepository.findByProductId(product.getId());
            List<ProductImage> productImages = productImageRepository.findByProductItemIdAndImageType(productItem.getId());
            List<ProductResponse> productResponseList = productResponses.getContent();
            productResponseList.add(ProductResponse.fromProduct(product, productImages));
            productResponses = new PageImpl<>(productResponseList, pageable, products.size());        }
        return productResponses;
    }

    @Override
    public Page<ProductResponse> getByTag(String tag, Pageable pageable) {
        Page<Product> products = productRepository.findByTag(tag, pageable);
        return mapToProductResponse(products);
    }

    @Override
    public Page<ProductResponse> filterProduct(String slug, Long categoryId, Long supplierId, BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest) {
        Page<Product> products = productRepository.filterProduct(slug, categoryId, supplierId, minPrice, maxPrice, pageRequest);
        return mapToProductResponse(products);
    }

    @Override
    public List<String> getImageUrlFromPublicId(Long productItemId) throws Exception {
        List<String> imageUrlList = new ArrayList<>();
        List<ProductImage> productImages = productImageRepository.findByProductItemId(productItemId);
        for (ProductImage productImage : productImages){
            String imageUrl = cloudinaryService.getResourceUrlFromCloudinary(ConstantKey.RESOURCE_TYPE_IMAGE, productImage.getImageUrl());
            imageUrlList.add(imageUrl);
        }
        return imageUrlList;
    }

    @Override
    public void deleteSoften(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Product not found")
        );
        product.setIsActive(false);
    }

    @Override
    public void deleteStiffen(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Product not found")
        );
        productRepository.delete(product);
        productItemRepository.delete(productItemRepository.findByProductId(id));
        productImageRepository.deleteAll(productImageRepository.findByProductItemId(id));
    }

//    private Page<ProductResponse> mapToProductResponse(Page<Product> products) {
//         return products.map(product -> {
//             ProductItem productItem = productItemRepository.findByProductId(product.getId());
//             if (productItem == null) {
//                 throw new DataNotFoundException("Product item not found");
//             }
//             ProductImage productImage = productImageRepository.findByProductItemIdAndImageType(productItem.getId());
//            return ProductResponse.fromProduct(product, productImage);
//        });
//    }
        private Page<ProductResponse> mapToProductResponse(Page<Product> products) {
            return products.map(product -> mapProductToProductResponse(product));
        }

            private ProductResponse mapProductToProductResponse(Product product) {
                ProductItem productItem = productItemRepository.findByProductId(product.getId());
                if (productItem == null) {
                    throw new DataNotFoundException("Product item not found");
                }
                List<ProductImage> productImages = productImageRepository.findByProductItemIdAndImageType(productItem.getId());
                return ProductResponse.fromProduct(product, productImages);
    }
}
