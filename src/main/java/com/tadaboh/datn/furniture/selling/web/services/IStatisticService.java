package com.tadaboh.datn.furniture.selling.web.services;


import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RevenueChartResponse;
import com.tadaboh.datn.furniture.selling.web.enums.GroupByType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IStatisticService {
    Optional<BigDecimal> getRevenue(String timeType, LocalDate customStartDate, LocalDate customEndDate);

    RevenueChartResponse getRevenueChartData(int year, GroupByType groupByType);
}
