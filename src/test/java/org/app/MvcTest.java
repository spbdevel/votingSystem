package org.app;

import org.app.entity.Dish;
import org.app.entity.Restaurant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes =  {TestConfig.class} )
public class MvcTest {

    private Logger logger = Logger.getLogger(MvcTest.class.getName());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void checkArrRestaurantsStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/rest/restaurants");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }


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
        Restaurant restaurant = restTemplate.getForObject("http://localhost:8081/rest/restaurant/3", Restaurant.class);
        logger.info("restaurant: " + restaurant.getId());
    }





}