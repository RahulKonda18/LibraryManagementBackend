# Library Management System - API Documentation

Base URL: `http://localhost:8080`

## Authentication & User Management

### 1. Register User
**Endpoint:** `POST /api/users/register`  
**Description:** Register a new user (admin or subscriber)  
**Request Body:**
```json
{
  "username": "newuser",
  "password": "password123",
  "name": "New User",
  "email": "newuser@example.com",
  "role": "SUBSCRIBER"
}
```
**Response:** `200 OK` - User object with ID and wallet balance  
**Error:** `400 Bad Request` - If username/email already exists  

### 2. Login User
**Endpoint:** `POST /api/users/login`  
**Description:** Authenticate user and return user details  
**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```
**Response:** `200 OK` - User object with all details  
**Error:** `401 Unauthorized` - If credentials are invalid  

### 3. Get All Users
**Endpoint:** `GET /api/users`  
**Description:** Get list of all users (admin only)  
**Response:** `200 OK` - Array of user objects  
**Example Response:**
```json
[
  {
    "id": 1,
    "username": "admin",
    "name": "Library Administrator",
    "email": "admin@library.com",
    "role": "ADMIN",
    "walletBalance": 0.00,
    "totalFinesPaid": 0.00
  }
]
```

### 4. Get Users by Role
**Endpoint:** `GET /api/users/role/{role}`  
**Description:** Get users filtered by role (ADMIN or SUBSCRIBER)  
**Path Parameters:** `role` - ADMIN or SUBSCRIBER  
**Response:** `200 OK` - Array of user objects  
**Error:** `400 Bad Request` - If role is invalid  

### 5. Get User by ID
**Endpoint:** `GET /api/users/{id}`  
**Description:** Get specific user details by ID  
**Path Parameters:** `id` - User ID  
**Response:** `200 OK` - User object  
**Error:** `404 Not Found` - If user doesn't exist  

### 6. Update User
**Endpoint:** `PUT /api/users/{id}`  
**Description:** Update user information  
**Path Parameters:** `id` - User ID  
**Request Body:**
```json
{
  "name": "Updated Name",
  "email": "updated@example.com",
  "password": "newpassword"
}
```
**Response:** `200 OK` - Updated user object  
**Error:** `404 Not Found` - If user doesn't exist  

### 7. Delete User
**Endpoint:** `DELETE /api/users/{id}`  
**Description:** Delete a user  
**Path Parameters:** `id` - User ID  
**Response:** `200 OK` - User deleted successfully  
**Error:** `404 Not Found` - If user doesn't exist  

## Wallet Management

### 8. Get Wallet Balance
**Endpoint:** `GET /api/users/{id}/wallet`  
**Description:** Get current wallet balance for a user  
**Path Parameters:** `id` - User ID  
**Response:** `200 OK` - Balance amount (BigDecimal)  
**Example Response:** `200.00`  
**Error:** `404 Not Found` - If user doesn't exist  

### 9. Get Total Fines Paid
**Endpoint:** `GET /api/users/{id}/fines`  
**Description:** Get total fines paid by a user  
**Path Parameters:** `id` - User ID  
**Response:** `200 OK` - Total fines amount (BigDecimal)  
**Example Response:** `25.00`  
**Error:** `404 Not Found` - If user doesn't exist  

### 10. Add Money to Wallet
**Endpoint:** `POST /api/users/{id}/wallet/add`  
**Description:** Add money to user's wallet  
**Path Parameters:** `id` - User ID  
**Request Body:**
```json
{
  "amount": 50.00
}
```
**Response:** `200 OK` - Money added successfully  
**Error:** `404 Not Found` - If user doesn't exist  

## Book Management

### 11. Get All Books
**Endpoint:** `GET /api/books`  
**Description:** Get paginated list of all books  
**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 9)
**Response:** `200 OK` - Paginated book objects  
**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "A Tale of Two Cities",
      "author": "Charles Dickens",
      "publishedYear": 1859,
      "genre": "Fiction",
      "copies": 11
    }
  ],
  "totalPages": 20,
  "totalElements": 173,
  "size": 9,
  "number": 0
}
```

### 12. Get Books by Genre
**Endpoint:** `GET /api/books/genre`  
**Description:** Get paginated list of books filtered by genre  
**Query Parameters:**
- `genre` (required): Genre name
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 9)
**Response:** `200 OK` - Paginated book objects  
**Example:** `GET /api/books/genre?genre=Fiction&page=0&size=9`  

### 13. Get Book by ID
**Endpoint:** `GET /api/books/{id}`  
**Description:** Get specific book details by ID  
**Path Parameters:** `id` - Book ID  
**Response:** `200 OK` - Book object  
**Error:** `404 Not Found` - If book doesn't exist  

### 14. Add New Book (Admin Only)
**Endpoint:** `POST /api/books`  
**Description:** Add a new book to the library  
**Request Body:**
```json
{
  "title": "New Book Title",
  "author": "Author Name",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 5
}
```
**Response:** `200 OK` - Created book object  
**Error:** `400 Bad Request` - If data is invalid  

### 15. Update Book (Admin Only)
**Endpoint:** `PUT /api/books/{id}`  
**Description:** Update book information  
**Path Parameters:** `id` - Book ID  
**Request Body:**
```json
{
  "title": "Updated Title",
  "author": "Updated Author",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 10
}
```
**Response:** `200 OK` - Updated book object  
**Error:** `404 Not Found` - If book doesn't exist  

### 16. Delete Book (Admin Only)
**Endpoint:** `DELETE /api/books/{id}`  
**Description:** Delete a book from the library  
**Path Parameters:** `id` - Book ID  
**Response:** `200 OK` - Book deleted successfully  
**Error:** `404 Not Found` - If book doesn't exist  

### 17. Update Book Copies (Admin Only)
**Endpoint:** `PUT /api/books/{id}/copies`  
**Description:** Update the number of copies for a book  
**Path Parameters:** `id` - Book ID  
**Request Body:**
```json
{
  "copies": 15
}
```
**Response:** `200 OK` - Updated book object  
**Error:** `404 Not Found` - If book doesn't exist  

### 18. Get All Genres
**Endpoint:** `GET /api/books/genres`  
**Description:** Get list of all available book genres  
**Response:** `200 OK` - Array of genre strings  
**Example Response:**
```json
["Fiction", "Fantasy/Sci-Fi", "Thriller/Mystery", "Children's Literature", "Non-Fiction", "Romance/Drama"]
```

## Borrowing Operations

### 19. Borrow Book
**Endpoint:** `POST /api/borrows/{userId}/books/{bookId}/borrow`  
**Description:** Borrow a book for a user  
**Path Parameters:**
- `userId` - User ID
- `bookId` - Book ID
**Response:** `200 OK` - Borrow record object  
**Error:** `400 Bad Request` - If book unavailable or already borrowed  
**Example Response:**
```json
{
  "id": 1,
  "user": {
    "id": 2,
    "username": "john_doe",
    "name": "John Doe",
    "role": "SUBSCRIBER",
    "walletBalance": 200.00
  },
  "book": {
    "id": 1,
    "title": "A Tale of Two Cities",
    "author": "Charles Dickens",
    "copies": 10
  },
  "borrowDate": "2025-08-22T10:23:24.408946",
  "returnDate": null,
  "dueDate": "2025-09-05T10:23:24.408946",
  "fineAmount": 0.00,
  "returned": false,
  "finePaid": false
}
```

### 20. Return Book
**Endpoint:** `POST /api/borrows/{userId}/books/{bookId}/return`  
**Description:** Return a borrowed book  
**Path Parameters:**
- `userId` - User ID
- `bookId` - Book ID
**Response:** `200 OK` - Updated borrow record with fine calculation  
**Error:** `400 Bad Request` - If no active borrow found  

### 21. Pay Fine
**Endpoint:** `POST /api/borrows/{userId}/fines/{borrowRecordId}/pay`  
**Description:** Pay fine for a returned book  
**Path Parameters:**
- `userId` - User ID
- `borrowRecordId` - Borrow record ID
**Response:** `200 OK` - Boolean indicating success  
**Error:** `400 Bad Request` - If insufficient wallet balance or fine already paid  

### 22. Get User Borrow History
**Endpoint:** `GET /api/borrows/{userId}/history`  
**Description:** Get complete borrowing history for a user  
**Path Parameters:** `userId` - User ID  
**Response:** `200 OK` - Array of borrow record objects  

### 23. Get User Active Borrows
**Endpoint:** `GET /api/borrows/{userId}/active`  
**Description:** Get currently borrowed books for a user  
**Path Parameters:** `userId` - User ID  
**Response:** `200 OK` - Array of active borrow record objects  

### 24. Get User Unpaid Fines
**Endpoint:** `GET /api/borrows/{userId}/unpaid-fines`  
**Description:** Get unpaid fines for a user  
**Path Parameters:** `userId` - User ID  
**Response:** `200 OK` - Array of borrow records with unpaid fines  

### 25. Get All Active Borrows (Admin Only)
**Endpoint:** `GET /api/borrows/active`  
**Description:** Get all active borrows across all users  
**Response:** `200 OK` - Array of active borrow record objects  

### 26. Get All Unpaid Fines (Admin Only)
**Endpoint:** `GET /api/borrows/unpaid-fines`  
**Description:** Get all unpaid fines across all users  
**Response:** `200 OK` - Array of borrow records with unpaid fines  

### 27. Get Total Fines Collected (Admin Only)
**Endpoint:** `GET /api/borrows/total-fines`  
**Description:** Get total fines collected by the library  
**Response:** `200 OK` - Total amount (BigDecimal)  
**Example Response:** `150.00`  

## Admin Operations

### 28. Get Total Fines Collected
**Endpoint:** `GET /api/admin/total-fines`  
**Description:** Get total fines collected by the library  
**Response:** `200 OK` - Total amount (BigDecimal)  

### 29. Get All Active Borrows
**Endpoint:** `GET /api/admin/active-borrows`  
**Description:** Get all active borrows across all users  
**Response:** `200 OK` - Array of active borrow record objects  

### 30. Get All Unpaid Fines
**Endpoint:** `GET /api/admin/unpaid-fines`  
**Description:** Get all unpaid fines across all users  
**Response:** `200 OK` - Array of borrow records with unpaid fines  

### 31. Get All Subscribers
**Endpoint:** `GET /api/admin/subscribers`  
**Description:** Get all users with SUBSCRIBER role  
**Response:** `200 OK` - Array of subscriber user objects  

### 32. Get All Admins
**Endpoint:** `GET /api/admin/admins`  
**Description:** Get all users with ADMIN role  
**Response:** `200 OK` - Array of admin user objects  

### 33. Get All Users
**Endpoint:** `GET /api/admin/users`  
**Description:** Get all users in the system  
**Response:** `200 OK` - Array of all user objects  

## Data Models

### User Object
```json
{
  "id": 1,
  "username": "john_doe",
  "password": "password123",
  "name": "John Doe",
  "email": "john@example.com",
  "role": "SUBSCRIBER",
  "walletBalance": 200.00,
  "totalFinesPaid": 0.00
}
```

### Book Object
```json
{
  "id": 1,
  "title": "A Tale of Two Cities",
  "author": "Charles Dickens",
  "publishedYear": 1859,
  "genre": "Fiction",
  "copies": 11
}
```

### Borrow Record Object
```json
{
  "id": 1,
  "user": {
    "id": 2,
    "username": "john_doe",
    "name": "John Doe",
    "role": "SUBSCRIBER",
    "walletBalance": 200.00
  },
  "book": {
    "id": 1,
    "title": "A Tale of Two Cities",
    "author": "Charles Dickens",
    "copies": 10
  },
  "borrowDate": "2025-08-22T10:23:24.408946",
  "returnDate": null,
  "dueDate": "2025-09-05T10:23:24.408946",
  "fineAmount": 0.00,
  "returned": false,
  "finePaid": false
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid request data"
}
```

### 401 Unauthorized
```json
{
  "error": "Authentication failed"
}
```

### 404 Not Found
```json
{
  "error": "Resource not found"
}
```

## Initial Test Data

### Admin User
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** `ADMIN`

### Subscriber Users
- **Username:** `john_doe` | **Password:** `password123`
- **Username:** `jane_smith` | **Password:** `password123`
- **Username:** `bob_wilson` | **Password:** `password123`

### Initial Wallet Balance
- All subscribers start with ₹200.00 in their wallet
- Library wallet starts with ₹0.00

## Business Rules

1. **Borrowing Period:** 14 days
2. **Fine Rate:** ₹5 per day for overdue books
3. **Initial Wallet Balance:** ₹200 for new subscribers
4. **Fine Payment:** Automatically deducted from user's wallet
5. **Book Copies:** Cannot borrow if no copies available
6. **Duplicate Borrows:** Cannot borrow the same book twice
7. **Role-based Access:** Admin can manage books, subscribers can borrow/return

## CORS Configuration
- CORS is enabled for `http://localhost:3000`
- All endpoints support cross-origin requests from the frontend
