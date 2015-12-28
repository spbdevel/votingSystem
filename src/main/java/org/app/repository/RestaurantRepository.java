package org.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.app.entity.Restaurant;
import org.springframework.stereotype.Repository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

}
