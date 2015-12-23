package org.app.repository;


import org.app.entity.Menu;
import org.app.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByName(String name);

    List<Menu> findByRestaurant(Restaurant restaurant);

    List<Menu> findByActiveToday(Boolean active);

}
