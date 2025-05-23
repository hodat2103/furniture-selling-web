package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlogRequest {
    private String title;

    private String content;

    @JsonProperty("employee_id")
    private Long employeeId;
}
