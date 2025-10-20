package com.Security.Controller;

import com.Security.Model.User;
import com.Security.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService service;

    @GetMapping("/security/get")
    public String get(){
        return "HELLO";
    }

    @PostMapping("/security/create")
    public ResponseEntity<?> create(@RequestBody User user){
        return new ResponseEntity<>(service.createUser(user),HttpStatus.OK);
    }
}
