package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.products.ProductVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVideoRepository extends JpaRepository<ProductVideo, Long> {
}
