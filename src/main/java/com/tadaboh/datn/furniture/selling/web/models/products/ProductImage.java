package com.tadaboh.datn.furniture.selling.web.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product_images")
public class ProductImage   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "image_url",nullable = false)
    private String imageUrl;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    @JsonIgnore
    private ProductItem productItem;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

    public ProductImage(ProductItem productItem, String imageUrl) {
        this.productItem = productItem;
        this.imageUrl = imageUrl;
    }

}
