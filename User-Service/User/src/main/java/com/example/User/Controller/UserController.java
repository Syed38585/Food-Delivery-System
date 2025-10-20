package com.example.User.Controller;

import com.example.User.Feign.FeignInterface;
import com.example.User.Model.User;
import com.example.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    FeignInterface feignInterface;

    private UserService service;

    UserController(UserService service){
        this.service = service;
    }

    long user_id;

    @Cacheable(value = "user", key = "#id")
    @GetMapping("/get/{id}")             // api to get user by id
    public ResponseEntity<User> getUser(@PathVariable long id){
        user_id = id;
        System.out.println(id);
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }


    @PostMapping("/create")   // api to create user
    public ResponseEntity<String> create(@RequestBody User user){
        return new ResponseEntity<>(service.createUser(user),HttpStatus.OK); // create user
    }

    @GetMapping("/get/user")     // api to get user
    public long getId(){
        long userId = feignInterface.getUserId(user_id);
        return userId;
    }
}
