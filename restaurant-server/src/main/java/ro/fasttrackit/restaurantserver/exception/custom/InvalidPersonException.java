package ro.fasttrackit.restaurantserver.exception.custom;

public class InvalidPersonException extends RuntimeException {
    public InvalidPersonException(String message) {
        super(message);
    }
}
