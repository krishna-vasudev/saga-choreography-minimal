package com.saga.common.events;
public class InventoryReservedEvent {
    public int orderId;
    public InventoryReservedEvent() {}
    public InventoryReservedEvent(int orderId) { this.orderId = orderId; }
}
