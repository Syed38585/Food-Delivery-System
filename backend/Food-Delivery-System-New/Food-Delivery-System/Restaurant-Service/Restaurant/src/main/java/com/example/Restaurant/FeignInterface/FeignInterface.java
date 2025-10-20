package com.example.Restaurant.FeignInterface;

import com.example.Restaurant.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER")
public interface FeignInterface {         //feign interface for user-service
    @GetMapping("/get/{id}")
    UserDto getUser(@PathVariable("id") long id);
}
