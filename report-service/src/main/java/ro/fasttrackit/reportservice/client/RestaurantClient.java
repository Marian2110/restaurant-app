package ro.fasttrackit.reportservice.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.reportservice.config.RestaurantRTConfig;
import ro.fasttrackit.restaurant.model.CollectionResponse;
import ro.fasttrackit.restaurant.model.Restaurant;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantClient {
    private final RestaurantRTConfig config;

    public CollectionResponse<Restaurant> getAllRestaurants(Integer page, Integer size) {
        return new RestTemplate()
                .exchange(
                        getUriComponentsBuilder()
                                .pathSegment("restaurants")
                                .queryParam("page", page)
                                .queryParam("size", size)
                                .toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(Map.of()),
                        new ParameterizedTypeReference<CollectionResponse<Restaurant>>() {
                        })
                .getBody();
    }

    private UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder.fromHttpUrl(config.location());
    }

    public Optional<Restaurant> getRestaurant(@NonNull Integer restaurantId) {
        var url = getUriComponentsBuilder()
                .pathSegment("restaurants", String.valueOf(restaurantId))
                .toUriString();
        return Optional.ofNullable(new RestTemplate().getForObject(url, Restaurant.class));
    }
}

