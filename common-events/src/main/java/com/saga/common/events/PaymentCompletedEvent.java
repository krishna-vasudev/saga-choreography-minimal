package com.saga.common.events;
public class PaymentCompletedEvent {
    public int orderId;
    public PaymentCompletedEvent() {}
    public PaymentCompletedEvent(int orderId){ this.orderId = orderId; }
}
