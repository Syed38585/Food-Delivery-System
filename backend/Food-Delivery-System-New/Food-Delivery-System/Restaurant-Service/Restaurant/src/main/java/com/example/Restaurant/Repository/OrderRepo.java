package com.example.Restaurant.Repository;

import com.example.Restaurant.Model.Order;
import com.example.Restaurant.Model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId ORDER BY o.createdAt DESC LIMIT 1")
    Order findTopByUserIdOrderByCreatedAtDesc(@Param("userId") long userId);


}
