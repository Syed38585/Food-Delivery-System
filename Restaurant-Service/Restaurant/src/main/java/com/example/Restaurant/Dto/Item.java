package com.example.Restaurant.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {         //item DTO
    private String name;
    private int quantity;
    private BigDecimal price;
}
