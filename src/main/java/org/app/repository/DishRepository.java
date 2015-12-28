package org.app.repository;


import org.app.entity.Dish;
import org.app.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface DishRepository extends JpaRepository<Dish, Long> {

    Dish findByName(String name);

    List<Dish> findByRestaurant(Restaurant restaurant);


}
