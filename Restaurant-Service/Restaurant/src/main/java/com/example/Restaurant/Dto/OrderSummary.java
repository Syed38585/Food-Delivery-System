package com.example.Restaurant.Dto;

import com.example.Restaurant.Model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderSummary {          // bill or order summary
    long userId;
    String name;
    List<OrderItems> list;
    Instant dateTime;
    BigDecimal totalAmount;
}
