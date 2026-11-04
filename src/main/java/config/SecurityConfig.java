package com.example.review.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Bật tính năng @PreAuthorize trong Controller
public class SecurityConfig {

    // 1. Cấu hình bảo mật cho các API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để test API dễ dàng
                .authorizeHttpRequests(auth -> auth
                        // API xem đánh giá: công khai, không cần đăng nhập
                        .requestMatchers(HttpMethod.GET, "/api/products/*/reviews").permitAll()

                        // API báo cáo: chỉ ADMIN
                        .requestMatchers("/api/reports/**").hasRole("ADMIN")

                        // API đánh giá: chỉ CUSTOMER
                        .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("CUSTOMER")

                        // Các đường dẫn còn lại: phải đăng nhập
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Dùng xác thực cơ bản (Username/Password)

        return http.build();
    }

    // 2. Mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Tạo user mẫu để test (In-memory)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // User CUSTOMER
        UserDetails customer = User.builder()
                .username("customer")
                .password(encoder.encode("123456"))
                .roles("CUSTOMER")
                .build();

        // User ADMIN
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, admin);
    }
}
