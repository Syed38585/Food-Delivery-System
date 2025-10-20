package com.example.Restaurant.Repository;

import com.example.Restaurant.Model.Restaurant;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestRepo extends JpaRepository<Restaurant,Long> {
    @Query
            ("SELECT r FROM Restaurant r WHERE " + "LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(r.address) LIKE LOWER(CONCAT('%', :keyword , '%'))")
            List<Restaurant> findBykeyword(@Param("keyword") String keyword);

    List<Restaurant> findByAddressAndCuisine(String address, String cuisine);

}
