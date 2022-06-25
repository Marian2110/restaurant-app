package ro.fasttrackit.restaurantserver.exception.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldError {
    private final String field;
    private final String errorMessage;
    private final String suggestion;
}
