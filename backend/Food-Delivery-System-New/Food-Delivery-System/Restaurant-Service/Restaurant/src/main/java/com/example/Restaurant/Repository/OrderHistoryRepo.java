package com.example.Restaurant.Repository;

import com.example.Restaurant.Model.Order;
import com.example.Restaurant.Model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory,Long> {

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.userId = :userId")
    List<OrderHistory> findByUserId(@Param("userId") Long userId);
}
