package ro.fasttrackit.restaurantserver.exception.custom;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class ResourceNotFoundException extends RuntimeException {
    private final Long id;
    private final String entityName;

    public ResourceNotFoundException(Long id, String entityName) {
        super(entityName + " with id " + id + " not found");
        this.entityName = entityName;
        this.id = id;
    }

    public static <T> ResourceNotFoundException forEntity(Class<T> entity, Long id) {
        String entityName = entity.getSimpleName();
        return createException(id, "Could not find " + entityName + " with id " + id, entityName);
    }

    private static ResourceNotFoundException createException(Long id, String errorMessage, String entityName) {
        ResourceNotFoundException resourceNotFoundException = ResourceNotFoundException.builder().entityName(entityName).id(id).build();
        log.error(errorMessage, id, resourceNotFoundException);
        return resourceNotFoundException;
    }
}
