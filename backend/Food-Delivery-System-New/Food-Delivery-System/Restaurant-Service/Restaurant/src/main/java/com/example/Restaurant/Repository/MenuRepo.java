package com.example.Restaurant.Repository;

import com.example.Restaurant.Model.MenuItems;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepo extends JpaRepository<MenuItems,Long> {
    @Query("SELECT m FROM MenuItems m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MenuItems> findByItem(@Param("keyword") String keyword);
}
