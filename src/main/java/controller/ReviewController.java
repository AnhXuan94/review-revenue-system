package com.example.review.controller;

import com.example.review.entity.Review;
import com.example.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 1. POST /api/reviews - Đánh giá sản phẩm (chỉ CUSTOMER đã mua)
    @PostMapping("/reviews")
    @PreAuthorize("hasRole('CUSTOMER')") // 🔥 Chỉ CUSTOMER mới được đánh giá
    public ResponseEntity<?> addReview(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int rating,
            @RequestParam String comment) {

        try {
            Review review = reviewService.addReview(userId, productId, rating, comment);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. GET /api/products/{id}/reviews - Xem đánh giá của sản phẩm
    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long id) {
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        return ResponseEntity.ok(reviews);
    }
}
