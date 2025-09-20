package com.saga.inventoryservice.events;
import com.saga.common.events.OrderCreatedEvent;
import com.saga.common.events.InventoryReservedEvent;
import com.saga.common.events.InventoryFailedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListeners {
    @Autowired KafkaTemplate<String,Object> kafka;
    @KafkaListener(topics = "order-created", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactory")
    public void handle(OrderCreatedEvent ev){
        // simple check: if quantity <=5 -> reserve
        System.out.println("Received order created");
        if(ev.quantity <= 5){ kafka.send("inventory-reserved", new InventoryReservedEvent(ev.orderId)); }
        else { kafka.send("inventory-failed", new InventoryFailedEvent(ev.orderId, "OUT_OF_STOCK")); }
    }
}
