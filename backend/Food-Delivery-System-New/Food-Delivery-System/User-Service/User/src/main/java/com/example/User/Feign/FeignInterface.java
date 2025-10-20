package com.example.User.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT")
public interface FeignInterface {
    @GetMapping("/get/user/{id}")
    long getUserId(@PathVariable("id") long id);
}
