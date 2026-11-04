package com.example.review.repository;

import com.example.review.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Kiểm tra User đã mua Product chưa (dùng cho chức năng đánh giá)
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    // Lấy tất cả Order trong khoảng thời gian (dùng cho báo cáo doanh thu)
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
