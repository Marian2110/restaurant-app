package ro.fasttrackit.restaurantserver.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.restaurant.model.Restaurant;
import ro.fasttrackit.restaurantserver.model.RestaurantEntity;
import ro.fasttrackit.restaurantserver.model.mapper.RestaurantMapper;
import ro.fasttrackit.restaurantserver.service.RestaurantService;
import ro.fasttrackit.restaurantserver.utils.CollectionResponse;
import ro.fasttrackit.restaurantserver.utils.PageInfo;
import ro.fasttrackit.restaurantserver.utils.RestaurantFilter;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantController {
    private final RestaurantService restaurantService;

    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public CollectionResponse<Restaurant> getAll(@Valid RestaurantFilter filter) {
        Page<RestaurantEntity> restaurants = restaurantService.getAll(filter);
        return CollectionResponse.<Restaurant>builder()
                .content(restaurantMapper.toApi(restaurants.getContent()))
                .pageInfo(PageInfo.builder()
                        .currentPage(restaurants.getPageable().getPageNumber())
                        .pageSize(restaurants.getSize())
                        .totalElements(restaurants.getNumberOfElements())
                        .totalPages(restaurants.getTotalPages())
                        .build())
                .build();
    }

    @GetMapping( "{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantMapper.toApi(restaurantService.getRestaurant(id));
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantMapper.toApi(restaurantService.addRestaurant(restaurantMapper.toEntity(restaurant)));
    }

    @PutMapping( "{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return restaurantMapper.toApi(restaurantService.updateRestaurant(id, restaurantMapper.toEntity(restaurant)));
    }

    @PatchMapping( "{id}")
    public Restaurant patchRestaurant(@PathVariable Long id, @RequestBody JsonPatch restaurant) {
        return restaurantMapper.toApi(restaurantService.patchRestaurant(id, restaurant));
    }


    @DeleteMapping("{id}")
    public Restaurant deleteRestaurant(@PathVariable Long id) {
        return restaurantMapper.toApi(restaurantService.deleteRestaurant(id));
    }
}
