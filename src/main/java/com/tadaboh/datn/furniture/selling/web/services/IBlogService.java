package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.BlogRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.BlogResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface IBlogService {
    BlogResponse create(BlogRequest blogRequest, MultipartFile file) throws IOException;
    BlogResponse update(Long id, BlogRequest blogRequest, MultipartFile file) throws IOException;
    void deleteSoften(Long id);
    void deleteHard(Long id);
    Page<BlogResponse> filterBlog(String slug, Long employeeId, PageRequest pageRequest);
}
