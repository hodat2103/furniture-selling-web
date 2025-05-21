package com.tadaboh.datn.furniture.selling.web.models.products;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product_videos")
public class ProductVideo   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    private ProductItem productItemId;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

}
