package org.app.controller;

import org.app.entity.Menu;
import org.app.entity.Restaurant;
import org.app.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/rest")
@RestController()
public class MenuController extends AbstractController {

    @Autowired
    private MenuRepository menuRepository;

    @RequestMapping(value = "/today_menus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> todayMenus() {
        return menuRepository.findByActiveToday(true);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/menu/add_rest/{restId}", method = RequestMethod.POST)
    public Menu add(@Valid @RequestBody Menu menu, @PathVariable("restId") Long rest_id) {
        Restaurant restau = restauRepository.findOne(rest_id);
        menu.setRestaurant(restau);
        menu.setCreated(new Date());
        return menuRepository.save(menu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/menu/activate/{menuId}", method = RequestMethod.GET)
    public Menu activate(@PathVariable("menuId") Long id) {
        Menu menu = menuRepository.findOne(id);
        menu.setActiveToday(true);
        return menuRepository.save(menu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/menu/deactivate/{menuId}", method = RequestMethod.GET)
    public Menu deactivate(@PathVariable("menuId") Long id) {
        Menu menu = menuRepository.findOne(id);
        menu.setActiveToday(false);
        return menuRepository.save(menu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/menu/del/{menuId}", method = RequestMethod.GET)
    public Boolean del(@PathVariable("menuId") Long id) {
        menuRepository.delete(id);
        return true;
    }

}
