# Financial Transactions API

Backend application for managing financial transactions, user accounts, and deposits.  
The project was created to demonstrate backend development skills using modern Java and Spring ecosystem tools.

---

## Features

- User account management
- Financial transaction processing
- Transaction history
- Deposit managment
- REST API architecture
- JWT authentication & authorization
- Validation and exception handling
- Database persistence
- Layered architecture
- DTO mapping
- Unit and integration tests
- Docker support
- Swagger/OpenAPI documentation

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- Swagger / OpenAPI
- JUnit / Mockito

---

## Architecture

The application follows layered backend architecture:

```text
Controller → Service → Repository → Database
```
---

## Authentication

The application uses JWT-based authentication.

### Authentication Flow

```text
/register
    ↓
User created in database

/login
    ↓
JWT token returned

Authorization: Bearer <token>
    ↓
Access secured endpoints
```

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Docs:

```text
http://localhost:8080/v3/api-docs
```

---

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register new user. Choose USER role to create user with Bank Account, choose ADMIN role for admin user without bank account |
| POST | `/auth/login` | Authenticate user |

---

### Transactions

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/transactions/transfer` | Transfer value between two accounts |
| POST | `/api/transactions/deposit` | Deposit value to the account |

---

### Transactions History

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/transactions-history?bankAccountId=` | Retrieve all historical transactions for given bank account |

---

### Bank Accounts

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/bank-accounts` | Retrieve all bank accounts |
| GET | `/api/bank-accounts/bank-account?bankAccountId=` | Retrieve given bank account with its transaction history |

---

### Time Deposit

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/time-deposits?bankAccountId` | Retrieve all time deposits for a given bank account |
| POST | `/api/time-deposits` | Open new time deposit |

---

### User Account

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Retrieve all user accounts |
| GET | `/api/users/user-account` | Retrieve given user account with its bank account ids |
| POST | `/api/users/{userId}` | Update user account |

---

## Running Locally

### Clone repository

```bash
git clone https://github.com/SiroWirdo/FinancialTransactions.git
cd FinancialTransactions
```

---

## Run with Docker

```bash
docker-compose up --build
```

---

## Run manually

```bash
./mvnw spring-boot:run
```

---

## Environment Variables

```env
DB_URL=jdbc:postgresql://localhost:5432/finance
DB_USERNAME=postgres
DB_PASSWORD=password
JWT_SECRET=your_secret_key
```

---

## Example Requests

### Register User

```http
POST /auth/register
Content-Type: application/json

{
  "userName": "admin",
  "firstName": "John",
  "lastName": "Cash",
  "password": "123456",
  "email": "john@example.com"
  "role": "USER"
}
```

---

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "123456"
}
```

---

### Create Transaction

```http
POST /api/transactions
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "amount": 150.50,
  "currency": "USD",
  "recipient": "John Doe"
}
```

---

## Testing

Run tests:

```bash
./mvnw test
```

---

## Future Improvements

- More deposit types
- Refresh tokens
- Redis caching
- Kafka event processing
- CI/CD pipeline
- Kubernetes deployment
- API versioning
- Role-based authorization

---

## Project Goals

This project was built to practice:

- backend application architecture
- secure REST API development
- JWT authentication
- database integration
- clean code practices
- Dockerized environments
- testing strategies

---

## Author

Marcin Chlebowski

GitHub:  
https://github.com/SiroWirdo
