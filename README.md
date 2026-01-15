JAVA-BUILDING-STORE

JAVA-BUILDING-STORE is a Spring Boot REST API for managing products in a building supply store.
The project focuses on clean backend architecture, RESTful design, and practical use of Spring Data JPA.

It supports full CRUD operations, product filtering, DTO-based communication, and partial updates for specific product fields such as price, discount, and stock quantity.

🚀 Features

✅ RESTful API built with Spring Boot

✅ Product management (Create, Read, Update, Delete)

✅ Product filtering by:

Category

Price range

Discounted products

Keyword search (case-insensitive)

✅ Partial updates using PATCH endpoints

✅ DTO pattern for request and response models

✅ Validation using Jakarta Validation

✅ Layered architecture (Controller → Service → Repository)

✅ Custom JPA queries using @Query

🛠️ Tech Stack

Java 17+

Spring Boot

Spring Web

Spring Data JPA

Hibernate

Jakarta Validation

Maven

H2 / PostgreSQL / MySQL (configurable)

REST API

📁 Project Structure
com.tu.javabuildingstore
│
├── controller      # REST controllers
├── service         # Business logic
├── repository      # JPA repositories
├── model           # JPA entities
├── dto             # Data Transfer Objects
├── mapper          # Entity ↔ DTO mappers
├── exception       # Custom exceptions
└── config          # Application configuration
