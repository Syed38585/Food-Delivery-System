package com.example.Restaurant.Controller;

import com.example.Restaurant.Dto.OrderRequest;
import com.example.Restaurant.Dto.OrderSummary;
import com.example.Restaurant.Model.Order;
import com.example.Restaurant.Model.OrderHistory;
import com.example.Restaurant.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class OrderController {


    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    long user_id;

    @PostMapping("/order")                                 // api to place an order
    public ResponseEntity<String> order(@RequestBody OrderRequest orderRequest){
        System.out.println("Received items" + orderRequest);
        user_id = orderRequest.getUser_id();
        return new ResponseEntity<>(service.createOrder(orderRequest), HttpStatus.OK);
    }

    @GetMapping("/all")                                     // api to get the order history
    public ResponseEntity<List<Order>> getAll(){
        return new ResponseEntity<>(service.find(),HttpStatus.OK);
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<OrderSummary> getOrder(@PathVariable UUID id){
//        OrderSummary summary = service.getById(id);
//        return ResponseEntity.ok(summary);
//    }

    @PostMapping("/delivered")                                    // api to update the delivery status
    public ResponseEntity<String> updateStatus(@RequestParam UUID order_id){
        service.updateStatus(order_id);
        return new ResponseEntity<>("String",HttpStatus.OK);
    }

    @GetMapping("/by")                                           // api to  get the most recent order
    public ResponseEntity<List<OrderHistory>> getOrders(){
        return new ResponseEntity<>(service.getOrderHistoryById(user_id),HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")                                 // api to get the user info ex - mobileNo, email etc
    public long getUserId(@PathVariable long id){
        return user_id;
    }

}
