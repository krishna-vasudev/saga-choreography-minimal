package com.saga.common.events;
public class InventoryFailedEvent {
    public int orderId;
    public String reason;
    public InventoryFailedEvent() {}
    public InventoryFailedEvent(int orderId, String reason){ this.orderId = orderId; this.reason = reason; }
}
