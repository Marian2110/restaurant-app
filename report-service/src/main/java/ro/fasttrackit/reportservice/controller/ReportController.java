package ro.fasttrackit.reportservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.reportservice.service.ReportService;
import ro.fasttrackit.restaurant.model.CollectionResponse;
import ro.fasttrackit.restaurant.model.Restaurant;

import java.util.Optional;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    // get all restaurants
    @GetMapping
    public CollectionResponse<Restaurant> getAllRestaurants(@RequestParam() Integer page,
                                                            @RequestParam() Integer size) {
        return reportService.getAllRestaurants(page, size);
    }

    @GetMapping(path = "{id}")
    public Optional<Restaurant> getRestaurant(@PathVariable Integer id) {
        return reportService.getRestaurant(id);
    }

}
