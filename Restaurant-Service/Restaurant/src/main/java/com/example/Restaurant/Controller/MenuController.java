package com.example.Restaurant.Controller;

import com.example.Restaurant.Dto.MenuItemsDto;
import com.example.Restaurant.Model.MenuItems;
import com.example.Restaurant.Service.MenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {

    MenuService service;

    MenuController(MenuService service){
        this.service = service;
    }

    @PostMapping("/add")                                     // api to add menu items
    public ResponseEntity<String> add(@RequestBody MenuItems menuItems){
        return new ResponseEntity<>(service.add(menuItems, menuItems.getRestaurant().getId()), HttpStatus.OK);
    }


    @Cacheable(value = "menu", key = "#id")
    @GetMapping("/get")                                               // api to get all the menu items irrespective of the
    public ResponseEntity<List<MenuItems>> get(){                      // restaurant
        return new ResponseEntity<>(service.getInfo(),HttpStatus.OK);
    }

    @GetMapping("/get/menu/by/{id}")
    public MenuItemsDto getBYId(@PathVariable long id){                  // api to get menu Item by ID
        MenuItems menuItems =  service.findById(id);
        MenuItemsDto menuItemsDto = MenuItemsDto.builder()
                .price(menuItems.getPrice())
                .name(menuItems.getName())
                .description(menuItems.getDescription())
                .build();
        return menuItemsDto;
    }
}
