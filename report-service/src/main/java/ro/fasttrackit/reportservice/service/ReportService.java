package ro.fasttrackit.reportservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.reportservice.client.RestaurantClient;
import ro.fasttrackit.restaurant.model.CollectionResponse;
import ro.fasttrackit.restaurant.model.Restaurant;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {

    private final RestaurantClient restaurantClient;

    public CollectionResponse<Restaurant> getAllRestaurants(Integer page, Integer size) {
        return restaurantClient.getAllRestaurants(page, size);
    }

    public Optional<Restaurant> getRestaurant(Integer restaurantId) {
        return restaurantClient.getRestaurant(restaurantId);
    }
}
