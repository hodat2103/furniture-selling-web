package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT o FROM Order o " +
            "WHERE (:keyword IS NULL OR o.code LIKE %:keyword%) " +
            "AND (:orderCode IS NULL OR o.code LIKE %:orderCode%) " +
            "AND (:startDateTime IS NULL OR o.orderDate >= :startDateTime) " +
            "AND (:endDateTime IS NULL OR o.orderDate <= :endDateTime)")
    Page<Order> searchOrders(
            String keyword,
            String fullName,
            String productName,
            String orderCode,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            org.springframework.data.domain.Pageable pageable);
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> findRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT MONTH(o.orderDate) as month, SUM(o.totalPrice) as revenue " +
            "FROM Order o WHERE YEAR(o.orderDate) = :year " +
            "GROUP BY MONTH(o.orderDate) ORDER BY month")
    List<Object[]> findMonthlyRevenueByYear(@Param("year") int year);

    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d') as day, " +
            "SUM(o.totalPrice) as revenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY day ORDER BY day")
    List<Object[]> findDailyRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT YEAR(o.orderDate) as year, WEEK(o.orderDate) as week, " +
            "SUM(o.totalPrice) as revenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY year, week ORDER BY year, week")
    List<Object[]> findWeeklyRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT YEAR(o.orderDate) as year, MONTH(o.orderDate) as month, " +
            "SUM(o.totalPrice) as revenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY year, month ORDER BY year, month")
    List<Object[]> findMonthlyRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT YEAR(o.orderDate) as year, QUARTER(o.orderDate) as quarter, " +
            "SUM(o.totalPrice) as revenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY year, quarter ORDER BY year, quarter")
    List<Object[]> findQuarterlyRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT YEAR(o.orderDate) as year, " +
            "SUM(o.totalPrice) as revenue " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY year ORDER BY year")
    List<Object[]> findYearlyRevenueBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
