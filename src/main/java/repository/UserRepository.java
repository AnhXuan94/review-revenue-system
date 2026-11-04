package com.example.review.repository;

import com.example.review.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm User theo username
    Optional<User> findByUsername(String username);
}
