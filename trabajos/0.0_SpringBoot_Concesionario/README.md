# Vehicle Management REST API
Designed as a scalable backend system following clean architecture principles.

REST API developed with **Java & Spring Boot** for vehicle management, including authentication, authorization and secure data handling.

## 🚀 Features

- JWT Authentication & Authorization
- User management
- Vehicle CRUD operations
- Role-based access control
- Data validation
- Custom error handling
- Relational database integration (MySQL)


## 🛠️ Technologies

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Hibernate
- JWT (JSON Web Token)
- MySQL
- Maven


## 📐 Architecture

The application follows a **layered architecture**:
    - Controller → handles HTTP requests
    - Service → business logic
    - Repository → database access
    - Security → authentication & authorization


## 🔑 Authentication

The API uses **JWT (JSON Web Token)** for secure authentication:
    - User logs in
    - Receives a token
    - Sends token in requests
    - Access is granted based on roles


## 📊 Domain & Functionality
The API manages multiple entities and relationships:
    - Users & Roles (authentication and authorization)
    - Vehicles
    - Brands
    - Types

Includes full CRUD operations, role-based access control and strucutred business logic.


## 🧠 Key Concepts Implemented

    - Layered architecture (Controller / Service / Repository)
    - Separation of concerns
    - Entity handling
    - Secure authentication with JWT
    - Role-based authorization
    - Error handling and validation


## 🌐 Setup & Run

    1. Clone the repository.
    2. Configure database in **application.properties**.
    3. Run the application using your IDE or Maven.


## 🎯 Purpose

This project was built to demonstrate backend development skills using **Spring Boot**, focusing on secure API design, clean architecture and best practices.


## 📌 Highlights

    - Secure REST API with JWT authentication
    - Multi-entity system with relational data model
    - Clean and maintainable backend architecture


## 📇 Author & Contact

**Alexandra**  
[LinkedIn](https://www.linkedin.com/in/alexandravalledor)
[GitHub](https://github.com/NalleTanuki)