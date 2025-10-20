package com.example.Restaurant.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto {    // dto for restaurant
    private Long id;
    private String name;
    private String address;
    private String cuisine;

    @OneToMany(mappedBy = "rest_id", cascade = CascadeType.ALL)
    List<MenuItemsDto> menuItemsDtoList;
}