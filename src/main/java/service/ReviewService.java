package com.example.review.service;

import com.example.review.entity.Product;
import com.example.review.entity.Review;
import com.example.review.entity.User;
import com.example.review.repository.OrderRepository;
import com.example.review.repository.ProductRepository;
import com.example.review.repository.ReviewRepository;
import com.example.review.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    // 1. Thêm đánh giá (có kiểm tra điều kiện)
    public Review addReview(Long userId, Long productId, int rating, String comment) {
        // Kiểm tra User và Product có tồn tại không
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // 🔥 ĐIỂM QUAN TRỌNG: Kiểm tra khách đã mua sản phẩm này chưa
        boolean daMuaHang = orderRepo.existsByUserIdAndProductId(userId, productId);
        if (!daMuaHang) {
            throw new RuntimeException("Bạn chỉ có thể đánh giá sản phẩm đã mua!");
        }

        // Nếu đã mua, cho phép đánh giá
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedDate(LocalDateTime.now());

        return reviewRepo.save(review);
    }

    // 2. Xem tất cả đánh giá của một sản phẩm
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepo.findByProductId(productId);
    }
}