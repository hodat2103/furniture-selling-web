package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RevenueChartResponse;
import com.tadaboh.datn.furniture.selling.web.enums.GroupByType;
import com.tadaboh.datn.furniture.selling.web.services.IStatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}statistics")
@Tag(name = "Statistic", description = "Statistic API")
public class StatisticController {
    private final IStatisticService statisticService;

    @GetMapping("/revenue")
    public ResponseEntity<ResponseSuccess> getRevenue(
            @RequestParam(name = "timeType", required = false) String timeType,
            @RequestParam(name = "customStartDate", required = false) LocalDate customStartDate,
            @RequestParam(name = "customEndDate", required = false) LocalDate customEndDate) {
        Optional<BigDecimal> revenues = statisticService.getRevenue(timeType, customStartDate, customEndDate);
        ResponseSuccess responseSuccess = new ResponseSuccess(
                HttpStatus.ACCEPTED,"Revenue retrieved successfully",
                revenues.orElse(BigDecimal.ZERO)
        );
        return ResponseEntity.ok(responseSuccess);
    }
    @GetMapping("/revenue/chart")
    public ResponseEntity<RevenueChartResponse> getRevenueChart(
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "MONTHLY") GroupByType groupBy) {

        return ResponseEntity.ok(
                statisticService.getRevenueChartData(
                        year != null ? year : LocalDate.now().getYear(),
                        groupBy
                )
        );
    }


}
