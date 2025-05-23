package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.BlogRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.BlogResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.users.Blog;
import com.tadaboh.datn.furniture.selling.web.models.users.Employee;
import com.tadaboh.datn.furniture.selling.web.repositories.BlogRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.EmployeeRepository;
import com.tadaboh.datn.furniture.selling.web.services.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService {
    private final BlogRepository blogRepository;
    private final EmployeeRepository employeeRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public BlogResponse create(BlogRequest blogRequest, MultipartFile imageFile) throws IOException {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(blogRequest.getEmployeeId()).orElseThrow(() -> new DataNotFoundException("Employee not found")));

        Blog blog = Blog.builder()
                .title(blogRequest.getTitle())
                .content(blogRequest.getContent())
                .slug(GenerateCode.generateSlug(blogRequest.getTitle()))
                .imageUrl(cloudinaryService.uploadFile(imageFile, null,"Blogs"))
                .employee(employee.get())
                .build();

        Blog insert = blogRepository.save(blog);
        return BlogResponse.fromBlog(insert);
    }

    @Override
    public BlogResponse update(Long id, BlogRequest blogRequest, MultipartFile imageFile) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Blog not found"));

        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setSlug(GenerateCode.generateSlug(blogRequest.getTitle()));
        if (imageFile != null) {
            try {
                blog.setImageUrl(cloudinaryService.uploadFile(imageFile, null,"Blogs"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Blog update = blogRepository.save(blog);

        return BlogResponse.fromBlog(update);
    }

    @Override
    public void deleteSoften(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Blog not found"));
        blog.setIsActive(false);
        blogRepository.save(blog);
    }

    @Override
    public void deleteHard(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Blog not found"));
        blogRepository.delete(blog);
    }

    @Override
    public Page<BlogResponse> filterBlog(String slug, Long employeeId, PageRequest pageRequest) {
        Page<Blog> blogs = blogRepository.filterBlog(slug, employeeId, pageRequest);
        return blogs.map(BlogResponse::fromBlog);
    }
}
