package com.example.Restaurant.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NonNull
    @Column(name = "user_id")
    private long userId;
    @NonNull
    private long rest_id;
    private BigDecimal total_amount;
    private String status = "PENDING";
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItems> items = new ArrayList<>();

    public void addItems(OrderItems orderItems) {
        items.add(orderItems);                  // add the new item
        orderItems.setOrder(this);              // link the item to this order
        if (total_amount == null) {
            total_amount = BigDecimal.ZERO;     // prevent null pointer
        }
        total_amount = total_amount.add(
                orderItems.getPrice().multiply(BigDecimal.valueOf(orderItems.getQuantity()))
        );
    }

}
