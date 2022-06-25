package ro.fasttrackit.restaurantserver.utils;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionResponse<T>(List<T> content, PageInfo pageInfo) {
}
