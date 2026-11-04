package com.example.review.repository;

import com.example.review.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Không cần thêm hàm đặc biệt, JpaRepository đã có sẵn findById, save, findAll...
}
