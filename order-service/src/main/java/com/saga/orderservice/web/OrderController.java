package com.saga.orderservice.web;
import com.saga.common.events.OrderCreatedEvent;
import com.saga.orderservice.domain.Order;
import com.saga.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired OrderRepository repo;
    @Autowired KafkaTemplate<String,Object> kafka;
    @PostMapping
    public Order create(@RequestBody Order order){
        System.out.println(order.toString());
        order.status = "PENDING";
        repo.save(order);
        kafka.send("order-created", new OrderCreatedEvent(order.id, order.productId, order.quantity));
        return order;
    }
    @GetMapping("/test/{id}")
    public Optional<Order> get(@PathVariable("id") int id){
        System.out.println(id);
        return repo.findById(id);
    }

    @GetMapping("/getall")
    public List<Order> ping() {
        List<Order> orders = repo.findAll();
        return orders;
    }
}
