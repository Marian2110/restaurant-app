package ro.fasttrackit.restaurantserver.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public record RestaurantFilter(
        List<Integer> stars,
        String city,
        @NotNull(message = "Page number is required")
        @Min(value = 0, message = "Page number must be positive")
        Integer page,
        @NotNull(message = "Page size is required")
        @Min(value = 0, message = "Page size must be positive")
        Integer size) {
    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
