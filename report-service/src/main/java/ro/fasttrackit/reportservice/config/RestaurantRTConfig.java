package ro.fasttrackit.reportservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("restaurant-service.rt")
public record RestaurantRTConfig(String location) {
}
