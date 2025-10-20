package com.example.Restaurant.Controller;

import com.example.Restaurant.Dto.RestaurantDto;
import com.example.Restaurant.Model.Restaurant;
import com.example.Restaurant.Service.RestaurantService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RController {

    RestaurantService service;

    RController(RestaurantService service){
        this.service = service;
    }

    @PostMapping("/add")                             // api to add a restaurant
    public ResponseEntity<String> addData(@RequestBody Restaurant restaurant){
        return new ResponseEntity<>(service.add(restaurant), HttpStatus.OK);
    }

    @Cacheable(value = "rest", key = "#restId")
    @GetMapping("/get")                                // api to get all the restaurants
    public ResponseEntity<List<Restaurant>> get() {
        return new ResponseEntity<>(service.getInfo(),HttpStatus.OK);
    }

    @Cacheable(value = "menu" , key = "#menu_id`")
    @GetMapping("/get/by")                             // api to fetch restaurant based on address, cuisine etc
    public ResponseEntity<List<Restaurant>> getByKey(@RequestParam("keyword") String keyword){
        return new ResponseEntity<>(service.getByKeyword(keyword),HttpStatus.OK);
    }

    @GetMapping("/get/by/{id}")                               //get restaurant by id
    public ResponseEntity<RestaurantDto> getById(@PathVariable long id){
        Restaurant restaurant = service.getRestaurantById(id);
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .address(restaurant.getAddress())
                .cuisine(restaurant.getCuisine())
                .name(restaurant.getName())
                .id(restaurant.getId())
                .build();
        return new ResponseEntity<>(restaurantDto,HttpStatus.OK);
    }

}

