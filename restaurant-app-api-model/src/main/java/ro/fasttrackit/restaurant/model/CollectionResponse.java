package ro.fasttrackit.restaurant.model;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionResponse<T>(List<T> content, PageInfo pageInfo) {
}
