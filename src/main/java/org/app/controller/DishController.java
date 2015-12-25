package org.app.controller;

import org.app.entity.Dish;
import org.app.entity.Restaurant;
import org.app.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/rest")
@RestController()
public class DishController  extends  AbstractController {

    private Logger logger = Logger.getLogger(DishController.class.getName());

    @Autowired
    private DishRepository dishRepository;


    @RequestMapping(value = "/dishes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> dishes() {
        return dishRepository.findAll();
    }


    @RequestMapping(value = "/dish/dishes/{restId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> dishesByRestaurant(@PathVariable("restId")Long id) {
        Restaurant restau = restauRepository.findOne(id);
        logger.info("restau: " + restau.getName());
        return dishRepository.findByRestaurant(restau);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/dish/add", method = RequestMethod.PUT)
    public Dish add(@Valid @RequestBody Dish dish) {
        Dish saved = dishRepository.save(dish);
        return saved;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/dish/add_dish_rest/{restId}", method = RequestMethod.PUT)
    public Dish add(@Valid @RequestBody Dish dish, @PathVariable("restId") Long rest_id) {
        Restaurant restau = restauRepository.findOne(rest_id);
        dish.setRestaurant(restau);
        Dish saved = dishRepository.save(dish);
        return saved;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/dish/del/{dishId}", method = RequestMethod.DELETE)
    public Boolean del(@PathVariable("dishId") Long id) {
        dishRepository.delete(id);
        return true;
    }


}
