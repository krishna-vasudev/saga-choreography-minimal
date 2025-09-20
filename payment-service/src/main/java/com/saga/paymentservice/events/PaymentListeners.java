package com.saga.paymentservice.events;
import com.saga.common.events.InventoryReservedEvent;
import com.saga.common.events.PaymentCompletedEvent;
import com.saga.common.events.PaymentFailedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class PaymentListeners {
    @Autowired KafkaTemplate<String,Object> kafka;
    private final Random rnd = new Random();
    @KafkaListener(topics = "inventory-reserved", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
    public void handle(InventoryReservedEvent ev){
        // simulate random payment success/fail
        System.out.println("Received inventory reserved event");
        boolean success = rnd.nextBoolean();
        if(success) kafka.send("payment-completed", new PaymentCompletedEvent(ev.orderId));
        else kafka.send("payment-failed", new PaymentFailedEvent(ev.orderId, "DECLINED"));
    }
}
