package com.saga.orderservice.domain;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String productId;
    public int quantity;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }

    public String status;
    public Order() {}
}
