# JWT Authentication Implementation

This document explains how JWT (JSON Web Token) authentication has been implemented in the Library Management Backend.

## Overview

The application now uses JWT tokens for authentication and authorization. When a user logs in successfully, a JWT token is generated and returned. This token must be included in the Authorization header for subsequent API calls.

## Authentication Flow

1. **User Registration**: Users can register without authentication
2. **User Login**: Users login with username/password and receive a JWT token
3. **API Access**: Include the JWT token in the Authorization header for protected endpoints

## API Endpoints

### Public Endpoints (No Authentication Required)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login
- `GET /api/health/**` - Health check endpoints

### Protected Endpoints (Authentication Required)

#### Admin Only Endpoints
- `GET /api/users` - Get all users
- `GET /api/users/role/{role}` - Get users by role
- `DELETE /api/users/{id}` - Delete user
- `POST /api/books` - Add new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book
- `PUT /api/books/{id}/copies` - Update book copies
- `GET /api/borrows/active` - Get all active borrows
- `GET /api/borrows/unpaid-fines` - Get all unpaid fines
- `GET /api/borrows/total-fines` - Get total fines collected
- All `/api/admin/**` endpoints

#### Subscriber Only Endpoints
- `POST /api/books/{id}/borrow` - Borrow a book
- `POST /api/books/{id}/return` - Return a book
- `POST /api/borrows/{userId}/books/{bookId}/borrow` - Borrow a book
- `POST /api/borrows/{userId}/books/{bookId}/return` - Return a book
- `POST /api/borrows/{userId}/fines/{borrowRecordId}/pay` - Pay fine

#### User-Specific Endpoints (Authenticated Users)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `GET /api/users/{id}/wallet` - Get wallet balance
- `GET /api/users/{id}/fines` - Get total fines paid
- `POST /api/users/{id}/wallet/add` - Add to wallet
- `GET /api/borrows/{userId}/history` - Get user borrow history
- `GET /api/borrows/{userId}/active` - Get user active borrows
- `GET /api/borrows/{userId}/unpaid-fines` - Get user unpaid fines

## How to Use JWT Authentication

### 1. Login to Get Token

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "your_username",
    "password": "your_password"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "username": "your_username",
    "name": "Your Name",
    "email": "your@email.com",
    "role": "SUBSCRIBER"
  },
  "message": "Login successful"
}
```

### 2. Use Token for Protected Endpoints

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## JWT Token Structure

The JWT token contains:
- **Subject**: Username
- **Role**: User role (ADMIN/SUBSCRIBER)
- **UserId**: User ID
- **Issued At**: Token creation time
- **Expiration**: Token expiration time (24 hours by default)

## Configuration

JWT settings can be configured in `application.properties`:

```properties
# JWT Configuration
jwt.secret=yourSecretKeyHereMakeItLongAndSecureForProductionUse
jwt.expiration=86400000
```

## Security Features

1. **Password Encryption**: All passwords are encrypted using BCrypt
2. **Token Expiration**: JWT tokens expire after 24 hours
3. **Role-Based Access Control**: Different endpoints require different user roles
4. **Stateless Authentication**: No server-side session storage
5. **CORS Support**: Configured for frontend integration

## Error Handling

- **401 Unauthorized**: Invalid or missing JWT token
- **403 Forbidden**: Valid token but insufficient permissions
- **400 Bad Request**: Invalid request format

## Frontend Integration

For frontend applications, store the JWT token (e.g., in localStorage) and include it in the Authorization header for all API requests:

```javascript
const token = localStorage.getItem('jwt_token');
const headers = {
  'Authorization': `Bearer ${token}`,
  'Content-Type': 'application/json'
};

fetch('/api/protected-endpoint', {
  headers: headers
});
```
