package com.example.seller.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.seller.entities.Seller;
import com.example.seller.repo.SellerRepository;
import java.util.UUID;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner demo(final SellerRepository sellerRepository) {
        return strings -> {
            Seller seller1 = new Seller(UUID.randomUUID(), "Dasha", "Orlova");
            sellerRepository.save(seller1);

        };
    }
}
