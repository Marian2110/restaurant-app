package ro.fasttrackit.restaurantserver.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.fasttrackit.restaurantserver.exception.custom.InvalidPersonException;
import ro.fasttrackit.restaurantserver.exception.custom.ResourceNotFoundException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ExceptionResponse handleResourceNotFound(ResourceNotFoundException ex) {
        return ExceptionResponse.builder()
                .internalCode("RNF01")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidPersonException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleResourceNotFound(InvalidPersonException ex) {
        return ExceptionResponse.builder()
                .internalCode("IPE01")
                .message(ex.getMessage())
                .build();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return ExceptionResponse.builder()
                .internalCode("IAE01")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ExceptionResponse handleAllExceptions(Exception ex) {
        log.error("Generic error", ex);
        return ExceptionResponse.builder()
                .internalCode("GEN01")
                .message("Internal server error")
                .build();
    }
}