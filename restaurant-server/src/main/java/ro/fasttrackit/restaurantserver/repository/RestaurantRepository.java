package ro.fasttrackit.restaurantserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.fasttrackit.restaurantserver.model.RestaurantEntity;

import java.util.Collection;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query("select r from RestaurantEntity r" +
            " where(r.stars in :starList)" +
            " and ((:city is null and r.city is null) or r.city like %:city%)")
    Page<RestaurantEntity> findByStarsInAndCityLike(@Param("starList") Collection<Integer> stars,
                                                    @Param("city") String city, Pageable pageable);

    Optional<Object> findByName(String name);

}
