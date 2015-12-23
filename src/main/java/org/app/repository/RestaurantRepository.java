package org.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.app.entity.Restaurant;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

}
