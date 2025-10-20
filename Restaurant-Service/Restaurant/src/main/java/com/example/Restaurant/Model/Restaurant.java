package com.example.Restaurant.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String address;
    String cuisine;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<MenuItems> menu;
}
