package com.example.Restaurant.Service;

import com.example.Restaurant.Model.MenuItems;
import com.example.Restaurant.Model.Restaurant;
import com.example.Restaurant.Repository.MenuRepo;
import com.example.Restaurant.Repository.RestRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    MenuRepo repo;

    RestRepo restRepo;

    MenuService(MenuRepo repo,RestRepo restRepo){
        this.repo = repo;
        this.restRepo = restRepo;
    }


    public String add(MenuItems menuItems,long rest_id) {            // add menu items
        Restaurant restaurant = restRepo.findById(rest_id).orElseThrow(() ->
                new RuntimeException("RESTAURANT NOT FOUND"));
        if(restaurant!=null) {
            MenuItems item = new MenuItems();
            item.setName(menuItems.getName());
            item.setDescription(menuItems.getDescription());
            item.setPrice(menuItems.getPrice());
            item.setAvailable(true);
            item.setRestaurant(restaurant);
            repo.save(item);
            return "DONE";
        }
        return "ERROR : RESTAURANT NOT FOUND";
    }

    public List<MenuItems> getInfo() {
        return repo.findAll();
    }         // fetch menuitems

    public List<MenuItems> getItem(String keyword) {          // method for fetching menu items based on keyword
        return repo.findByItem(keyword);
    }

    public MenuItems findById(long id) {                        // fetching menu item based on id
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("ITEM NOT FOUND"));
    }
}
