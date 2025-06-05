package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.tadaboh.datn.furniture.selling.web.configurations.CloudinaryConfig;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import com.tadaboh.datn.furniture.selling.web.repositories.ProductImageRepository;
import com.tadaboh.datn.furniture.selling.web.utils.ConstantKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    private final CloudinaryConfig cloudinaryConfig;

    private final ProductImageRepository productImageRepository;



    public List<ProductImage> uploadMultipleImages(ProductItem productItem, List<MultipartFile> files) throws IOException {
        List<ProductImage> savedImages = new ArrayList<>();
//        List<ProductImage> existingImages = productImageRepository.findByProductItemId(productItem.getId());
//        if (existingImages.size() + files.size() > 5) {
//            throw new IllegalArgumentException("Total images cannot exceed 5");
//        }

        int index = 0;
        Map<String, Object> uploadResult = new HashMap<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileNameUnique = file.getOriginalFilename() + "_" + UUID.randomUUID();
                Map result = cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", ConstantKey.RESOURCE_TYPE_IMAGE,
                                "public_id", fileNameUnique
                        ));
//                String imageUrl = result.get("secure_url").toString();
                ProductImage productImage = new ProductImage();
                productImage.setProductItem (productItem);
                productImage.setImageUrl((String) result.get("public_id"));
                productImage.setImageType(index == 0 ? ConstantKey.MAIN_IMAGE : ConstantKey.SUB_IMAGE);
                productImage.setUploadAt(LocalDateTime.now());
                index++;

                savedImages.add(productImageRepository.save(productImage));
            }
        }
        return savedImages;
    }
    public String uploadFile(MultipartFile file, String fileName, String folder) throws IOException {
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }
            String originalFilename = file.getOriginalFilename(); ;
            String uniqueFileName;
            if(fileName == null) {
                // Sinh tên file duy nhất
                uniqueFileName = folder + "_" +
                        (originalFilename != null ? originalFilename.substring(0, originalFilename.lastIndexOf(".")) : ".jpg");
            } else {
                uniqueFileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            String selectFolder = folder.isEmpty() ? "root/" : (folder + "/"); // chọn folder upload
            // Log thông tin file
            logger.info("Uploading file: {}", uniqueFileName);
            // Upload với chi tiết log
            Map<?, ?> uploadResult = cloudinaryConfig.cloudinary().uploader()
                    .upload(file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", selectFolder,
                                    "public_id", uniqueFileName,
                                    "overwrite", true
                            )
                    );

            // Log kết quả upload
            logger.info("Upload successful. Response: {}", uploadResult);
            // Trả về URL an toàn
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            logger.error("Error uploading file to Cloudinary", e);
            throw e;
        }
    }
    public void deleteFile(String imageUrl) {
        try {
            // Extract public_id from the URL
            String publicId = getResourceUrlFromCloudinary(ConstantKey.RESOURCE_TYPE_IMAGE,imageUrl);

            cloudinaryConfig.cloudinary().uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            logger.error("Error deleting file from Cloudinary", e);
        }
    }
    public String getResourceUrlFromCloudinary(String resourceType, String publicId) throws Exception {
        ApiResponse resource =  cloudinaryConfig.cloudinary().api().    resource(publicId, ObjectUtils.asMap(
                "resource_type", resourceType
        ));

        // url from map resource
        return (String) resource.get("url");
    }
}
