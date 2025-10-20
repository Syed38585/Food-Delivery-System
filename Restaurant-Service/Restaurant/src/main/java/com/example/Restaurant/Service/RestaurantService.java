package com.example.Restaurant.Service;

import com.example.Restaurant.Model.Restaurant;
import com.example.Restaurant.Repository.MenuRepo;
import com.example.Restaurant.Repository.RestRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    RestRepo repo;

    MenuRepo menuRepo;

    RestaurantService(RestRepo repo,MenuRepo menuRepo){
        this.repo = repo;
        this.menuRepo = menuRepo;
    }

    public String add(Restaurant restaurant) {   // method for adding restaurant
        repo.save(restaurant);
        return "DONE";
    }

    public List<Restaurant> getInfo() {         // get all restaurants
        Pageable pageable = PageRequest.of(0,10);
        Page<Restaurant> page = repo.findAll(pageable);
        return page.getContent();
    }

    public String find(Long id){                  // find restaurant based on ID
        Restaurant r =  repo.findById(id).orElseThrow(() ->
                 new RuntimeException("NULL"));
        return r.getName();
    }

    public List<Restaurant> findAll() {
        return repo.findAll();
    }    // find all restaurants


    public List<Restaurant> getByKeyword(String keyword) {       // fetch restaurant based on keyword
        return repo.findBykeyword(keyword);
    }

    public Restaurant getRestaurantById(long id) {
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("RESTAURANT NOT FOUND"));
    }
}
