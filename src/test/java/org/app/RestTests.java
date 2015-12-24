package org.app;

import org.app.entity.Dish;
import org.app.entity.Restaurant;
import org.app.srv.cnfg.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes =  {TestConfig.class} )
public class RestTests {

    private Logger logger = Logger.getLogger(RestTests.class.getName());


    @Test
    public void testCreateRestaurnat() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Restaurant rest = new Restaurant();
        rest.setName("restaurant 3");
        rest.setDescription("description rest ");
        Restaurant restaurant = restTemplate.postForObject("http://localhost:8081/rest/restaurant/add", rest, Restaurant.class);
        logger.info("restaurant: " + restaurant.getId());
    }



    @Test
    public void testCreateDish() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Restaurant restaurant = restTemplate.getForObject("http://localhost:8081/rest/restaurant/3", Restaurant.class);
        logger.info("restaurant: " + restaurant.getId());
        Dish dish = new Dish();
        dish.setRestaurant(restaurant);
        dish.setName("dish 2");
        dish.setDescription("description dish ");
        dish.setPrice(124.5);
        dish = restTemplate.postForObject("http://localhost:8081/rest/dish/add", dish, Dish.class);
        logger.info("dish: " + dish.getId());
    }


   @Test
    public void testrest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Restaurant restaurant = restTemplate.getForObject("http://localhost:8081/rest/restaurant/1", Restaurant.class);
        logger.info("restaurant: " + restaurant.getId());
    }



}