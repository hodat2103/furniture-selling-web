package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.tadaboh.datn.furniture.selling.web.models.bases.AuditableEntity;
import com.tadaboh.datn.furniture.selling.web.models.users.Blog;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse extends AuditableEntity {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private String imageUrl;
    private Long employeeId;
    private Boolean isActive;

    public static BlogResponse fromBlog(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .slug(blog.getSlug())
                .imageUrl(blog.getImageUrl())
                .isActive(blog.getIsActive())
                .build();
    }
}
