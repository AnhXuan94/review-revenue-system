package com.example.review.controller;

import com.example.review.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // GET /api/reports/revenue?type=month - Xem báo cáo doanh thu (chỉ ADMIN)
    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')") // 🔥 CHỈ ADMIN mới được xem báo cáo
    public ResponseEntity<?> getRevenue(@RequestParam String type) {
        try {
            Map<String, Object> report = reportService.getRevenueReport(type);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}