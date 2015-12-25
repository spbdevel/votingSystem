package org.app.controller;

import org.app.entity.Restaurant;
import org.app.repository.RestaurantRepository;
import org.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/rest")
@RestController()
public class RestaurantController extends AbstractController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


    @RequestMapping(value = "/restaurants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> todayRestaurants() {
        return restaurantRepository.findAll();
    }

    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant restaurant(@PathVariable("restaurantId")Long id) {
        Restaurant rest = restaurantRepository.findOne(id);
        return rest;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/restaurant/add", method = RequestMethod.PUT)
    public Restaurant add(@Valid @RequestBody Restaurant restaurant) {
        Restaurant rest = restaurantRepository.save(restaurant);
        return rest;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/restaurant/del/{restaurantId}", method = RequestMethod.DELETE)
    public Boolean del(@PathVariable("restaurantId")Long id) {
        restaurantRepository.delete(id);
        return true;
    }


}
