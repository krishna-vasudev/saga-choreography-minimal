package com.saga.common.events;

public class OrderCreatedEvent {
    public int orderId;
    public String productId;
    public int quantity;

    public OrderCreatedEvent() {}
    public OrderCreatedEvent(int orderId, String productId, int quantity) {
        this.orderId = orderId; this.productId = productId; this.quantity = quantity;
    }
}
