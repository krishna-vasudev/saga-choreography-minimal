package com.saga.orderservice.events;
import com.saga.common.events.PaymentCompletedEvent;
import com.saga.common.events.PaymentFailedEvent;
import com.saga.common.events.InventoryFailedEvent;
import com.saga.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class OrderEventListeners {
    @Autowired OrderRepository repo;
    @KafkaListener(topics = "payment-completed", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void paymentCompleted(PaymentCompletedEvent ev){
        System.out.println("Received payment completed");
        repo.findById(ev.orderId).ifPresent(o->{ o.status="COMPLETED"; repo.save(o); });
    }
    @KafkaListener(topics = "inventory-failed", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void inventoryFailed(InventoryFailedEvent ev){
        System.out.println("Received inventory failed");
        repo.findById(ev.orderId).ifPresent(o->{ o.status="CANCELLED"; repo.save(o); });
    }
    @KafkaListener(topics = "payment-failed", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void paymentFailed(PaymentFailedEvent ev){
        System.out.println("Received payment failed");
        repo.findById(ev.orderId).ifPresent(o->{ o.status="CANCELLED"; repo.save(o); });
    }
}
