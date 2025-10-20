package com.example.Restaurant.Service;

import com.example.Restaurant.Dto.*;
import com.example.Restaurant.FeignInterface.FeignInterface;
import com.example.Restaurant.FeignInterface.MsgInterface;
import com.example.Restaurant.Model.*;
import com.example.Restaurant.Repository.OrderHistoryRepo;
import com.example.Restaurant.Repository.OrderRepo;
import com.example.Restaurant.Repository.RestRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService {

    @Autowired
    FeignInterface feign;


    @Autowired
    MsgInterface msgInterface;

    private static final String kafka = "sms";
    private static  final String kafkaMail = "mail";

    private final OrderRepo orderRepo;
    private final RestaurantService service;
    private final OrderHistoryRepo orderHistoryRepo;
    private final RestRepo repo;
    private UserDto userDto;

    ExecutorService executors = Executors.newFixedThreadPool(10);

    long user_id;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    List<OrderItems> list;

    public OrderService(OrderRepo orderRepo, RestaurantService service,
                        OrderHistoryRepo orderHistoryRepo, RestRepo repo,UserDto userDto) {
        this.orderRepo = orderRepo;
        this.service = service;
        this.orderHistoryRepo = orderHistoryRepo;
        this.repo = repo;
        this.userDto = userDto;
    }

    @Transactional
    public String createOrder(OrderRequest orderRequest) {      // method for placing order
        if (orderRequest.getItemsList() == null || orderRequest.getItemsList().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        user_id = orderRequest.getUser_id();

        UserDto userDto1 = feign.getUser(orderRequest.getUser_id());
        Restaurant restaurant = repo.findById(orderRequest.getRest_id()).orElseThrow(() ->
                new RuntimeException("NULL"));

        if(userDto1!=null && restaurant!=null){

        Order order = new Order(orderRequest.getUser_id(), orderRequest.getRest_id());

        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (Item itemDto : orderRequest.getItemsList()) {
            BigDecimal unitPrice = itemDto.getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalOrderPrice = totalOrderPrice.add(itemTotal);

            OrderItems orderItem = new OrderItems(itemDto.getName(), itemDto.getQuantity(), unitPrice);

            orderItem.setOrder(order);
            order.addItems(orderItem);
        }


        OrderHistory orderHistory = OrderHistory.builder()
                .userId(orderRequest.getUser_id())
                .restName(service.find(orderRequest.getRest_id()))
                .item(orderRequest.getItemsList().toString())
                .orderedAt(LocalDateTime.now())
                .build();

        orderRepo.save(order);

        sendSms();

        order.setStatus("PREPARING");

        orderHistoryRepo.save(orderHistory);

        return "DONE";
        }else{
            throw new RuntimeException("NULL");
        }
    }

    public OrderSummary getById(UUID id) {                  // get summary of order
        Order order = orderRepo.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));

        Restaurant restaurant = repo.findById(order.getRest_id()).orElseThrow(() ->
                new RuntimeException("Restaurant not found"));

        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new OrderSummary(
                order.getUserId(),
                restaurant.getName(),
                order.getItems(),
                order.getCreatedAt(),
                totalAmount
        );
    }

    public Order getLatestOrder(long id) {
    return orderRepo.findTopByUserIdOrderByCreatedAtDesc(id);
    }  //fetch the most recent order


    public List<Order> find() {
        return orderRepo.findAll();
    }       // get order history

    public void updateStatus(UUID id) {                              //update order status
        Order order = orderRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Order not found"));
        order.setStatus("DELIVERED");
    }

    public List<OrderHistory> getOrderHistoryById(long id) {
        return orderHistoryRepo.findByUserId(id);
    }

    public void sendEmail(){       // send email
        userDto = feign.getUser(user_id);
      kafkaTemplate.send(kafkaMail,userDto.toString());
    }

    @Async
    public void sendSms() {             // method for sending sms

        executors.submit( () -> {


            UserDto dto = feign.getUser(user_id);
            if (dto == null) {
                System.out.println("User not found");
                return;
            }



            LocalDateTime dateTime = LocalDateTime.now();
            String content = "YOUR ORDER FOR HAS BEEN SUCCESSFULLY PLACED AT " + dateTime.atOffset(ZoneOffset.ofHoursMinutesSeconds(12,60,60)) +  "\n";

            TwilioDto dto1 = new TwilioDto(dto.getPhoneNo(), content);

            kafkaTemplate.send(kafka,dto1.toString());
        });
    }
}

