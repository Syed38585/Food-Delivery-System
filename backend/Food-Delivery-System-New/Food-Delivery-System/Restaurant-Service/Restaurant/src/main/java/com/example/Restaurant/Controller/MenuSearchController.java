package com.example.Restaurant.Controller;

import com.example.Restaurant.Model.MenuItems;
import com.example.Restaurant.Service.MenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MenuSearchController {

    private MenuService menuService;

    MenuSearchController(MenuService menuService){
        this.menuService = menuService;
    }


    @Cacheable(value = "menu" , key = "#menu_id")
    @GetMapping("/search")                                           // api to fetch menu item by searching
    public ResponseEntity<List<MenuItems>> searchMenu(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(menuService.getItem(keyword));
    }
}

