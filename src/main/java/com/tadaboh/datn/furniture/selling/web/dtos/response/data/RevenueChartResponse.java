package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevenueChartResponse {
    private List<String> labels;
    private List<BigDecimal> revenues;
    private List<String> timeRanges;
    private BigDecimal totalRevenue;
    private String currency;
}
