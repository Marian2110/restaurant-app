package ro.fasttrackit.restaurant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Restaurant(Long id, String name, Integer stars, String city,
                         @JsonSerialize(using = LocalDateSerializer.class)
                         @JsonDeserialize(using = LocalDateDeserializer.class)
                         LocalDate since) {
}
