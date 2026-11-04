package com.example.review.repository;

import com.example.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Lấy tất cả đánh giá của một sản phẩm
    List<Review> findByProductId(Long productId);
}
