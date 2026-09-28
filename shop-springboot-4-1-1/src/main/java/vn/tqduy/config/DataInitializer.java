package vn.tqduy.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.tqduy.entity.Product;
import vn.tqduy.entity.Role;
import vn.tqduy.entity.User;
import vn.tqduy.repository.ProductRepository;
import vn.tqduy.repository.RoleRepository;
import vn.tqduy.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));

            User admin = userRepository.findByUsername("admin").orElseGet(() -> {
                User u = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Administrator")
                        .role(adminRole)
                        .enabled(true)
                        .build();
                return userRepository.save(u);
            });

            User demoUser = userRepository.findByUsername("trungnh").orElseGet(() -> {
                User u = User.builder()
                        .username("trungnh")
                        .email("trungnhspkt@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Nguyễn Hữu Trung")
                        .role(userRole)
                        .enabled(true)
                        .build();
                return userRepository.save(u);
            });

            if (productRepository.count() == 0) {
                Product p1 = Product.builder()
                        .name("Điện thoại Oppo A95")
                        .description("Điện thoại thông minh Oppo A95 màn hình AMOLED, sạc nhanh 33W")
                        .price(new BigDecimal("6565656.00"))
                        .imageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400|mock_oppo_a95")
                        .user(demoUser)
                        .createdAt(LocalDateTime.now())
                        .build();

                Product p2 = Product.builder()
                        .name("Điện thoại Oppo A6")
                        .description("Oppo A6 cấu hình mạnh mẽ, pin trâu 5000mAh")
                        .price(new BigDecimal("689990.00"))
                        .imageUrl("https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=400|mock_oppo_a6")
                        .user(demoUser)
                        .createdAt(LocalDateTime.now())
                        .build();

                productRepository.save(p1);
                productRepository.save(p2);
            }
        };
    }
}
