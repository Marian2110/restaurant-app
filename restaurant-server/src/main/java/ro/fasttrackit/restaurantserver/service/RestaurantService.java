package ro.fasttrackit.restaurantserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ro.fasttrackit.restaurant.model.Restaurant;
import ro.fasttrackit.restaurantserver.exception.custom.ResourceNotFoundException;
import ro.fasttrackit.restaurantserver.model.RestaurantEntity;
import ro.fasttrackit.restaurantserver.model.mapper.RestaurantMapper;
import ro.fasttrackit.restaurantserver.repository.RestaurantRepository;
import ro.fasttrackit.restaurantserver.utils.RestaurantFilter;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public Page<RestaurantEntity> getAll(RestaurantFilter filter) {
        log.info("Stars: {}", filter.stars());
        log.info("City: {}", filter.city());
        List<Integer> stars = filter.stars() == null ? List.of(1, 2, 3, 4, 5) : filter.stars();
        return restaurantRepository
                .findByStarsInAndCityLike(
                        stars,
                        filter.city(),
                        filter.getPageable()
                );
    }

    public RestaurantEntity getRestaurant(Long id) {
        return restaurantRepository
                .findById(id)
                .orElseThrow(() -> getResourceNotFoundException(id));
    }

    public RestaurantEntity addRestaurant(RestaurantEntity restaurantEntity) {
        validatedEntity(restaurantEntity);
        return restaurantRepository.save(restaurantEntity.withId(0L));
    }

    public RestaurantEntity updateRestaurant(Long id, RestaurantEntity toEntity) {
        return restaurantRepository
                .findById(id)
                .map(restaurantEntity -> {
                    restaurantEntity.setName(toEntity.getName());
                    restaurantEntity.setStars(toEntity.getStars());
                    restaurantEntity.setCity(toEntity.getCity());
                    restaurantEntity.setSince(toEntity.getSince());
                    return restaurantRepository.save(restaurantEntity);
                })
                .orElseThrow(() -> getResourceNotFoundException(id));
    }

    private ResourceNotFoundException getResourceNotFoundException(Long id) {
        return ResourceNotFoundException.forEntity(RestaurantEntity.class, id);
    }

    private void validatedEntity(RestaurantEntity restaurantEntity) {
        if (restaurantRepository.findByName(restaurantEntity.getName()).isPresent()) {
            throw new IllegalArgumentException("Restaurant with the same name already exists " + restaurantEntity.getName());
        }
        if (restaurantEntity.getSince().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Since date cannot be in the future");
        }
    }

    public RestaurantEntity deleteRestaurant(Long id) {
        return restaurantRepository
                .findById(id)
                .map(restaurantEntity -> {
                    restaurantRepository.delete(restaurantEntity);
                    return restaurantEntity;
                })
                .orElseThrow(() -> getResourceNotFoundException(id));
    }

    public RestaurantEntity patchRestaurant(Long id, JsonPatch restaurant) {
        return restaurantRepository
                .findById(id)
                .map(restaurantEntity -> {
                    ObjectMapper jsonMapper = new ObjectMapper();
                    JsonNode jsonNode = jsonMapper
                            .convertValue(
                                    mapper.toApi(restaurantEntity),
                                    JsonNode.class
                            );
                    try {
                        JsonNode patchedJson = restaurant.apply(jsonNode);
                        return mapper.toEntity(jsonMapper.treeToValue(patchedJson, Restaurant.class));
                    } catch (JsonPatchException | JsonProcessingException e) {
                        throw new IllegalArgumentException("Invalid patch + " + e.getMessage());
                    }
                })
                .orElseThrow(() -> getResourceNotFoundException(id));
    }
}
