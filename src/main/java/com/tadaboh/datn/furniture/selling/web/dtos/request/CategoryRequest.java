package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryRequest {
    private String name;

    private String description;

    @JsonProperty("parent_id")
    private Long parentId;

}
