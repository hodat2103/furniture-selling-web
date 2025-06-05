package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.controllers.admin.StatisticController;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.RevenueChartResponse;
import com.tadaboh.datn.furniture.selling.web.enums.GroupByType;
import com.tadaboh.datn.furniture.selling.web.repositories.OrderRepository;
import com.tadaboh.datn.furniture.selling.web.services.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final OrderRepository orderRepository;

    @Override
    public Optional<BigDecimal> getRevenue(String timeType, LocalDate customStartDate, LocalDate customEndDate) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        // If both custom dates are null, check timeType
        if (customStartDate == null && customEndDate == null) {
            if (timeType == null) {
                throw new IllegalArgumentException("Either timeType or customStartDate and customEndDate must be provided");
            }
        }

        // Handle timeType cases
        if (timeType != null) {
            LocalDate endDate = LocalDate.now(); // Default to today
            LocalDate startDate;

            switch (timeType) {
                case "today":
                    startDate = LocalDate.now();
                    break;
                case "yesterday":
                    startDate = LocalDate.now().minusDays(1);
                    endDate = startDate;
                    break;
                case "last7days":
                    startDate = LocalDate.now().minusDays(7);
                    break;
                case "thisWeek":
                    startDate = LocalDate.now().with(DayOfWeek.MONDAY);
                    break;
                case "lastWeek":
                    startDate = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
                    endDate = startDate.plusDays(6);
                    break;
                case "thisMonth":
                    startDate = LocalDate.now().withDayOfMonth(1);
                    break;
                case "lastMonth":
                    startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                    break;
                case "thisYear":
                    startDate = LocalDate.now().withDayOfYear(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time type");
            }

            // Convert to LocalDateTime at start and end of day
            startDateTime = startDate.atStartOfDay();
            endDateTime = endDate.atTime(LocalTime.MAX); // 23:59:59.999999999
        }
        else if (customStartDate != null && customEndDate != null) {
            // Convert custom dates to LocalDateTime
            startDateTime = customStartDate.atStartOfDay();
            endDateTime = customEndDate.atTime(LocalTime.MAX);
        }
        else {
            throw new IllegalArgumentException("Either timeType or customStartDate and customEndDate must be provided");
        }

        // Call repository to fetch revenue data within the date range
        return orderRepository.findRevenueBetweenDates(startDateTime, endDateTime);
    }

    @Override
    public RevenueChartResponse getRevenueChartData(int year, GroupByType groupBy) {
        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        List<Object[]> rawData;
        switch (groupBy) {
            case DAILY:
                rawData = orderRepository.findDailyRevenueBetweenDates(startDate, endDate);
                break;
            case WEEKLY:
                rawData = orderRepository.findWeeklyRevenueBetweenDates(startDate, endDate);
                break;
            case MONTHLY:
                rawData = orderRepository.findMonthlyRevenueBetweenDates(startDate, endDate);
                break;
            case QUARTERLY:
                rawData = orderRepository.findQuarterlyRevenueBetweenDates(startDate, endDate);
                break;
            case YEARLY:
                rawData = orderRepository.findYearlyRevenueBetweenDates(startDate, endDate);
                break;
            default:
                throw new IllegalArgumentException("Invalid group by type");
        }

        return mapToChartResponse(rawData, groupBy, year);
    }

    private RevenueChartResponse mapToChartResponse(List<Object[]> rawData, GroupByType groupBy, int baseYear) {
        RevenueChartResponse response = new RevenueChartResponse();
        response.setCurrency("VND");
        BigDecimal total = BigDecimal.ZERO;

        for (Object[] row : rawData) {
            if (response.getLabels() == null) {
                response.setLabels(new ArrayList<>());
            }
            if (response.getRevenues() == null) {
                response.setRevenues(new ArrayList<>());
            }
            if (response.getTimeRanges() == null) {
                response.setTimeRanges(new ArrayList<>());
            }

            String label = generateLabel(row, groupBy, baseYear);
            BigDecimal revenue = (BigDecimal) row[row.length - 1]; // Revenue is always last column

            response.getLabels().add(label);
            response.getRevenues().add(revenue);
            response.getTimeRanges().add(generateDisplayLabel(row, groupBy, baseYear));

            total = total.add(revenue);
        }

        response.setTotalRevenue(total);
        return response;
    }

    private String generateLabel(Object[] row, GroupByType groupBy, int baseYear) {
        switch (groupBy) {
            case DAILY:
                return (String) row[0]; // Already formatted as YYYY-MM-DD
            case WEEKLY:
                return baseYear + "-W" + String.format("%02d", row[1]);
            case MONTHLY:
                return baseYear + "-" + String.format("%02d", row[1]);
            case QUARTERLY:
                return baseYear + "-Q" + row[1];
            case YEARLY:
                return String.valueOf(row[0]);
            default:
                throw new IllegalArgumentException("Invalid group by type");
        }
    }

    private String generateDisplayLabel(Object[] row, GroupByType groupBy, int baseYear) {
        switch (groupBy) {
            case DAILY:
                return "Ngày " + ((String) row[0]).substring(8);
            case WEEKLY:
                return "Tuần " + row[1] + " - " + baseYear;
            case MONTHLY:
                return "Tháng " + row[1] + "/" + baseYear;
            case QUARTERLY:
                return "Quý " + row[1] + " - " + baseYear;
            case YEARLY:
                return "Năm " + row[0];
            default:
                throw new IllegalArgumentException("Invalid group by type");
        }
    }
}
