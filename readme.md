# Saga Choreography Minimal

A minimal Spring Boot microservices project demonstrating **Saga Pattern using Choreography** with Kafka for event-driven communication. This project simulates order processing with inventory and payment services, showing how services remain consistent without distributed transactions.

---

## Overview

This project implements a **Saga Pattern (Choreography)**:

- **Order Service**: Receives orders and triggers `OrderCreatedEvent`.
- **Inventory Service**: Listens to order events and reserves inventory.
- **Payment Service**: Processes payment after inventory is confirmed.
- **Event-driven communication**: Kafka propagates events across services.

Each service reacts to events independently, and rollbacks are handled via compensating events.

---

## Architecture

```
  [Order Service] --(OrderCreatedEvent)--> [Inventory Service] --(InventoryReservedEvent)--> [Payment Service]
          ^                                                           |
          |                                                           v
   (OrderCancelledEvent)<---------------------------------------(PaymentFailedEvent)
```

- **Choreography**: No central orchestrator; services communicate via events.

---

## Technologies Used

- Spring Boot 3.x  
- Spring Data JPA  
- Spring Kafka  
- H2 Database (for testing; replaceable with MySQL/Postgres)  
- Kafka & Zookeeper (Dockerized)  
- Maven for build management  

---

## Modules

1. **order-service**: Handles order creation and status updates.  
2. **inventory-service**: Manages inventory reservation and release.  
3. **payment-service**: Handles payment processing and rollbacks.  
4. **common-events**: Contains shared event classes used across services.

---

## Setup

### Prerequisites

- Java 17+  
- Maven 3.8+  
- Docker Desktop (for Kafka & Zookeeper)  
- Postman / HTTP client

---

### Kafka Setup with Docker

```bash
cd <project-root>
docker-compose up -d
```

This starts:

- **Zookeeper**: `localhost:2181`  
- **Kafka Broker**: `localhost:9092`

---

### Running Services

1. Build all modules:

```bash
mvn clean install
```

2. Run each service individually:

```bash
cd order-service
mvn spring-boot:run

cd inventory-service
mvn spring-boot:run

cd payment-service
mvn spring-boot:run
```

> Ports:
> - Order Service: `8081`  
> - Inventory Service: `8082`  
> - Payment Service: `8083`

---

## API Endpoints

### Order Service

- **POST** `/orders`  
  Create a new order:

```json
{
  "productId": "prod-101",
  "quantity": 2
}
```

- **GET** `/orders/test/{id}`  
  Fetch order by UUID.

Example curl request:

```bash
curl -X POST http://localhost:8081/orders \
     -H "Content-Type: application/json" \
     -d '{"productId":"prod-101","quantity":2}'
     
curl -X GET http://localhost:8081/orders/test/<UUID>
```

### Inventory Service

- Listens to `OrderCreatedEvent` to reserve inventory.
- Publishes `InventoryReservedEvent` or `OrderCancelledEvent`.

### Payment Service

- Listens to `InventoryReservedEvent` for payment.
- Publishes `PaymentProcessedEvent` or `PaymentFailedEvent`.

---

## Kafka Topics

- `order-created`  
- `inventory-reserved`  
- `payment-processed`  
- `order-cancelled`  

> Services consume relevant topics and publish the next event.

---

## Notes

- **H2 Database** is for simplicity; data resets on restart.  
- UUID is recommended for `Order.id`.  
- Kafka must be running before starting services.  
- For production, replace H2 with MySQL/Postgres and configure Kafka brokers.

---

## Example Event Flow

1. **Create Order** → Order Service publishes `OrderCreatedEvent`.  
2. **Reserve Inventory** → Inventory Service consumes event, reserves stock, publishes `InventoryReservedEvent`.  
3. **Process Payment** → Payment Service consumes `InventoryReservedEvent`, processes payment, publishes `PaymentProcessedEvent`.  
4. **Rollback** → If payment fails, `PaymentFailedEvent` triggers order cancellation in Order Service.

---

## License

MIT License