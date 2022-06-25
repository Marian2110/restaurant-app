package ro.fasttrackit.restaurantserver.model.mapper;

import org.springframework.stereotype.Component;
import ro.fasttrackit.restaurant.model.Restaurant;
import ro.fasttrackit.restaurantserver.model.RestaurantEntity;

@Component
public class RestaurantMapper implements ModelMapper<Restaurant, RestaurantEntity> {
    @Override
    public Restaurant toApi(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .stars(restaurantEntity.getStars())
                .city(restaurantEntity.getCity())
                .since(restaurantEntity.getSince())
                .build();
    }

    @Override
    public RestaurantEntity toEntity(Restaurant restaurant) {
        return RestaurantEntity.builder()
                .id(restaurant.id())
                .name(restaurant.name())
                .stars(restaurant.stars())
                .city(restaurant.city())
                .since(restaurant.since())
                .build();
    }

}
