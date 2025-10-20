package com.example.Restaurant.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {        // DTO for order request i.e order request from frontend
    private long user_id;
    private long rest_id;
    @JsonProperty("itemsList")
    private List<Item> itemsList;

}
