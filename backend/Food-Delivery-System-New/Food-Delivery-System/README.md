# 🍽️ Food Delivery System – Microservices Architecture

## Overview
This project is a scalable, cloud-native food delivery platform built using a microservices architecture. It consists of six decoupled services: `User`, `Restaurant`, `Security`, `Messaging`, `Eureka Server`, and `API Gateway`. Each service is independently deployable and communicates via REST APIs and service discovery mechanisms.

## Architecture Summary

| Service        | Description                                                                 |
|----------------|-----------------------------------------------------------------------------|
| User Service   | Manages customer profiles, addresses, order history, and authentication context |
| Restaurant Service | Handles restaurant registration, menu management, availability, and order intake |
| Security Service | Centralized OAuth2-based authentication and JWT token issuance/validation |
| Messaging Service | Asynchronous notification delivery via SMS/email using message queues |
| Eureka Server  | Service registry for dynamic discovery and load balancing of microservices |
| API Gateway    | Entry point for all client requests; handles routing, CORS, rate limiting, and token validation |

All services are containerized and orchestrated using Docker Compose or Kubernetes (optional). Inter-service communication is secured and optimized for fault tolerance.

---

## 🔧 Tech Stack

- **Java 17**, **Spring Boot 3.x**
- **Spring Cloud Netflix Eureka**, **Spring Cloud Gateway**
- **Spring Security**, **OAuth2**, **JWT**
- **Kafka** (for messaging)
- **PostgreSQL** (per-service schema)
- **Feign Clients**, **RestTemplate**
- **Docker**, **Docker Compose**
- **Actuator**, **Micrometer** for observability

---

## 🧩 Microservices Breakdown

### 1. User Service
- REST endpoints for user registration, login, profile updates
- Stores user metadata and order history
- Integrates with Security Service for token validation
- Publishes events to Messaging Service on order status changes

### 2. Restaurant Service
- CRUD operations for restaurants and menus
- Handles incoming orders and updates availability
- Communicates with User Service for user context
- Secured via JWT tokens issued by Security Service

### 3. Security Service
- Implements OAuth2 login (Google/GitHub)
- Issues JWT tokens with custom claims
- Validates tokens for downstream services
- Centralized CORS and authentication configuration

### 4. Messaging Service
- Listens to order and user events via RabbitMQ/Kafka
- Sends SMS/email notifications using external providers
- Retry and dead-letter queue support for failed deliveries

### 5. Eureka Server
- Service registry for dynamic discovery
- Enables load-balanced communication between services
- Health checks and instance metadata propagation

### 6. API Gateway
- Routes external traffic to internal services
- Applies global filters (authentication, logging, rate limiting)
- Path rewriting, and fallback mechanisms

---


