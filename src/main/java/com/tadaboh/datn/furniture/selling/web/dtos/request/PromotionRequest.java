package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tadaboh.datn.furniture.selling.web.validator.CustomDateValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {

    @JsonProperty("title")
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @JsonProperty("description")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    @JsonProperty("is_active")
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean isActive;

    @JsonProperty("category_ids")
    @NotEmpty(message = "Phải chọn ít nhất một danh mục")
    private List<Long> categoryIds;

    @JsonProperty("apply_to_subcategories")
    @NotNull(message = "Giá trị này không được để trống")
    private Boolean applyToSubCategories;

    @JsonProperty("discount_value")
    @NotNull(message = "Giá trị giảm giá không được để trống")
    @Positive(message = "Giá trị giảm giá phải lớn hơn 0")
    private BigDecimal discountValue;

    @JsonProperty("start_date")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    @Future(message = "Ngày bắt đầu phải ở tương lai")
    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateValidator.class)
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải ở tương lai")
    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateValidator.class)
    private LocalDateTime endDate;
}
