package com.saga.orderservice.repository;
import com.saga.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface OrderRepository extends JpaRepository<Order, Integer> {}
