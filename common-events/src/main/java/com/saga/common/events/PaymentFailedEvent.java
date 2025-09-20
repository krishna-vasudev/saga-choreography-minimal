package com.saga.common.events;
public class PaymentFailedEvent {
    public int orderId;
    public String reason;
    public PaymentFailedEvent() {}
    public PaymentFailedEvent(int orderId, String reason){ this.orderId = orderId; this.reason = reason; }
}
