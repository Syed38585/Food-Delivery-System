package com.example.Restaurant.Dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto {
                                       //DTO for user
    String name;
    String phoneNo;
    String email;
    String address;
}
