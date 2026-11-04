package com.example.review.service;

import com.example.review.entity.Order;
import com.example.review.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepo;

    // Thống kê doanh thu theo ngày/tháng/năm
    public Map<String, Object> getRevenueReport(String type) {
        LocalDateTime startDate;
        LocalDateTime endDate = LocalDateTime.now();

        // Xác định khoảng thời gian dựa vào type
        if ("day".equalsIgnoreCase(type)) {
            startDate = endDate.minusDays(1);
        } else if ("month".equalsIgnoreCase(type)) {
            startDate = endDate.minusMonths(1);
        } else if ("year".equalsIgnoreCase(type)) {
            startDate = endDate.minusYears(1);
        } else {
            throw new RuntimeException("Type không hợp lệ. Chỉ chấp nhận: day, month, year");
        }

        // Lấy tất cả Order trong khoảng thời gian
        List<Order> orders = orderRepo.findByOrderDateBetween(startDate, endDate);

        // Tính tổng doanh thu
        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Đóng gói kết quả
        Map<String, Object> report = new HashMap<>();
        report.put("type", type);
        report.put("startDate", startDate);
        report.put("endDate", endDate);
        report.put("totalRevenue", totalRevenue);
        report.put("orderCount", orders.size());

        return report;
    }
}
