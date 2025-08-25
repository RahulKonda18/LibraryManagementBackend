# Library Management API Documentation

This document provides comprehensive information for frontend developers to integrate with the Library Management Backend API, including JWT authentication.

## Base URL
```
http://localhost:8080
```

## Authentication

The API uses JWT (JSON Web Token) authentication. All protected endpoints require a valid JWT token in the Authorization header.

### Authentication Flow

1. **Register** a new user (optional - for new users)
2. **Login** to get a JWT token
3. **Include the token** in all subsequent API requests

### Headers Format
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

## API Endpoints

### 1. Authentication Endpoints

#### Register User
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "string",
  "password": "string",
  "name": "string",
  "email": "string",
  "role": "SUBSCRIBER" | "ADMIN"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "password": "$2a$10$...",
  "name": "John Doe",
  "email": "john@example.com",
  "role": "SUBSCRIBER",
  "walletBalance": 200.00,
  "totalFinesPaid": 0.00
}
```

#### Login User
```http
POST /api/users/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "username": "john_doe",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "SUBSCRIBER",
    "walletBalance": 200.00,
    "totalFinesPaid": 0.00
  },
  "message": "Login successful"
}
```

### 2. Book Management

#### Get All Books (Paginated)
```http
GET /api/books?page=0&size=9
Authorization: Bearer <token>
```

**Response:**
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
  "pageable": {
    "pageNumber": 0,
    "pageSize": 9,
    "sort": {"empty": true, "sorted": false, "unsorted": true},
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 20,
  "totalElements": 173,
  "last": false,
  "size": 9,
  "number": 0,
  "numberOfElements": 9,
  "first": true,
  "empty": false
}
```

#### Get Books by Genre
```http
GET /api/books/genre?genre=Fiction&page=0&size=9
Authorization: Bearer <token>
```

#### Get Book by ID
```http
GET /api/books/{id}
Authorization: Bearer <token>
```

#### Get All Genres
```http
GET /api/books/genres
Authorization: Bearer <token>
```

**Response:**
```json
["Fiction", "Fantasy/Sci-Fi", "Thriller/Mystery", "Children's Literature", "Non-Fiction", "Romance/Drama"]
```

#### Add New Book (Admin Only)
```http
POST /api/books
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "title": "New Book Title",
  "author": "Author Name",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 5
}
```

#### Update Book (Admin Only)
```http
PUT /api/books/{id}
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "title": "Updated Book Title",
  "author": "Updated Author",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 10
}
```

#### Delete Book (Admin Only)
```http
DELETE /api/books/{id}
Authorization: Bearer <admin_token>
```

#### Update Book Copies (Admin Only)
```http
PUT /api/books/{id}/copies
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "copies": 15
}
```

#### Borrow Book (Subscriber Only)
```http
POST /api/books/{id}/borrow
Authorization: Bearer <subscriber_token>
```

#### Return Book (Subscriber Only)
```http
POST /api/books/{id}/return
Authorization: Bearer <subscriber_token>
```

### 3. User Management

#### Get All Users (Admin Only)
```http
GET /api/users
Authorization: Bearer <admin_token>
```

#### Get Users by Role (Admin Only)
```http
GET /api/users/role/{role}
Authorization: Bearer <admin_token>
```

#### Get User by ID
```http
GET /api/users/{id}
Authorization: Bearer <token>
```

#### Update User
```http
PUT /api/users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Name",
  "email": "updated@email.com",
  "password": "newpassword"
}
```

#### Delete User (Admin Only)
```http
DELETE /api/users/{id}
Authorization: Bearer <admin_token>
```

#### Get User Wallet Balance
```http
GET /api/users/{id}/wallet
Authorization: Bearer <token>
```

**Response:**
```json
200.00
```

#### Get User Total Fines Paid
```http
GET /api/users/{id}/fines
Authorization: Bearer <token>
```

#### Add Money to Wallet
```http
POST /api/users/{id}/wallet/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "amount": 50.00
}
```

### 4. Borrow Management

#### Borrow Book (with user ID)
```http
POST /api/borrows/{userId}/books/{bookId}/borrow
Authorization: Bearer <subscriber_token>
```

#### Return Book (with user ID)
```http
POST /api/borrows/{userId}/books/{bookId}/return
Authorization: Bearer <subscriber_token>
```

#### Pay Fine
```http
POST /api/borrows/{userId}/fines/{borrowRecordId}/pay
Authorization: Bearer <subscriber_token>
```

#### Get User Borrow History
```http
GET /api/borrows/{userId}/history
Authorization: Bearer <token>
```

#### Get User Active Borrows
```http
GET /api/borrows/{userId}/active
Authorization: Bearer <token>
```

#### Get User Unpaid Fines
```http
GET /api/borrows/{userId}/unpaid-fines
Authorization: Bearer <token>
```

#### Get All Active Borrows (Admin Only)
```http
GET /api/borrows/active
Authorization: Bearer <admin_token>
```

#### Get All Unpaid Fines (Admin Only)
```http
GET /api/borrows/unpaid-fines
Authorization: Bearer <admin_token>
```

#### Get Total Fines Collected (Admin Only)
```http
GET /api/borrows/total-fines
Authorization: Bearer <admin_token>
```

### 5. Admin Endpoints

#### Get Total Fines Collected
```http
GET /api/admin/total-fines
Authorization: Bearer <admin_token>
```

#### Get All Active Borrows
```http
GET /api/admin/active-borrows
Authorization: Bearer <admin_token>
```

#### Get All Unpaid Fines
```http
GET /api/admin/unpaid-fines
Authorization: Bearer <admin_token>
```

#### Get All Subscribers
```http
GET /api/admin/subscribers
Authorization: Bearer <admin_token>
```

#### Get All Admins
```http
GET /api/admin/admins
Authorization: Bearer <admin_token>
```

#### Get All Users
```http
GET /api/admin/users
Authorization: Bearer <admin_token>
```

## Error Responses

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or missing JWT token"
}
```

### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "Insufficient permissions"
}
```

### 400 Bad Request
```json
{
  "error": "Bad Request",
  "message": "Invalid request format"
}
```

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found"
}
```

## Frontend Integration Examples

### JavaScript/TypeScript

#### Authentication Service
```typescript
class AuthService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'jwt_token';

  async register(userData: {
    username: string;
    password: string;
    name: string;
    email: string;
    role: 'SUBSCRIBER' | 'ADMIN';
  }) {
    const response = await fetch(`${this.baseUrl}/api/users/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    return response.json();
  }

  async login(credentials: { username: string; password: string }) {
    const response = await fetch(`${this.baseUrl}/api/users/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });
    const data = await response.json();
    
    if (data.token) {
      localStorage.setItem(this.tokenKey, data.token);
      localStorage.setItem('user', JSON.stringify(data.user));
    }
    
    return data;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('user');
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    const user = this.getUser();
    return user?.role === 'ADMIN';
  }

  isSubscriber(): boolean {
    const user = this.getUser();
    return user?.role === 'SUBSCRIBER';
  }
}
```

#### API Service with Authentication
```typescript
class ApiService {
  private baseUrl = 'http://localhost:8080';
  private authService = new AuthService();

  private async request(endpoint: string, options: RequestInit = {}) {
    const token = this.authService.getToken();
    
    const headers = {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    };

    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      ...options,
      headers,
    });

    if (response.status === 401) {
      this.authService.logout();
      window.location.href = '/login';
      throw new Error('Unauthorized');
    }

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return response.json();
  }

  // Book endpoints
  async getBooks(page = 0, size = 9) {
    return this.request(`/api/books?page=${page}&size=${size}`);
  }

  async getBooksByGenre(genre: string, page = 0, size = 9) {
    return this.request(`/api/books/genre?genre=${genre}&page=${page}&size=${size}`);
  }

  async getBook(id: number) {
    return this.request(`/api/books/${id}`);
  }

  async addBook(bookData: any) {
    return this.request('/api/books', {
      method: 'POST',
      body: JSON.stringify(bookData),
    });
  }

  async borrowBook(bookId: number) {
    return this.request(`/api/books/${bookId}/borrow`, {
      method: 'POST',
    });
  }

  async returnBook(bookId: number) {
    return this.request(`/api/books/${bookId}/return`, {
      method: 'POST',
    });
  }

  // User endpoints
  async getUser(id: number) {
    return this.request(`/api/users/${id}`);
  }

  async updateUser(id: number, userData: any) {
    return this.request(`/api/users/${id}`, {
      method: 'PUT',
      body: JSON.stringify(userData),
    });
  }

  async getWalletBalance(userId: number) {
    return this.request(`/api/users/${userId}/wallet`);
  }

  async addToWallet(userId: number, amount: number) {
    return this.request(`/api/users/${userId}/wallet/add`, {
      method: 'POST',
      body: JSON.stringify({ amount }),
    });
  }

  // Borrow endpoints
  async getBorrowHistory(userId: number) {
    return this.request(`/api/borrows/${userId}/history`);
  }

  async getActiveBorrows(userId: number) {
    return this.request(`/api/borrows/${userId}/active`);
  }

  async payFine(userId: number, borrowRecordId: number) {
    return this.request(`/api/borrows/${userId}/fines/${borrowRecordId}/pay`, {
      method: 'POST',
    });
  }

  // Admin endpoints
  async getAllUsers() {
    return this.request('/api/users');
  }

  async getTotalFines() {
    return this.request('/api/admin/total-fines');
  }

  async getAllActiveBorrows() {
    return this.request('/api/borrows/active');
  }
}
```

#### React Hook Example
```typescript
import { useState, useEffect } from 'react';

export const useAuth = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const authService = new AuthService();

  useEffect(() => {
    const token = authService.getToken();
    const userData = authService.getUser();
    
    if (token && userData) {
      setUser(userData);
    }
    setLoading(false);
  }, []);

  const login = async (credentials: { username: string; password: string }) => {
    try {
      const data = await authService.login(credentials);
      setUser(data.user);
      return data;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  return {
    user,
    loading,
    login,
    logout,
    isAuthenticated: authService.isAuthenticated(),
    isAdmin: authService.isAdmin(),
    isSubscriber: authService.isSubscriber(),
  };
};
```

### React Component Example
```typescript
import React, { useState, useEffect } from 'react';
import { useAuth } from './hooks/useAuth';
import { ApiService } from './services/ApiService';

const BookList: React.FC = () => {
  const { user, isAuthenticated } = useAuth();
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const apiService = new ApiService();

  useEffect(() => {
    if (isAuthenticated) {
      loadBooks();
    }
  }, [isAuthenticated]);

  const loadBooks = async () => {
    try {
      const data = await apiService.getBooks();
      setBooks(data.content);
    } catch (error) {
      console.error('Error loading books:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleBorrow = async (bookId: number) => {
    try {
      await apiService.borrowBook(bookId);
      loadBooks(); // Refresh the list
    } catch (error) {
      console.error('Error borrowing book:', error);
    }
  };

  if (!isAuthenticated) {
    return <div>Please log in to view books</div>;
  }

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>Available Books</h2>
      <div className="books-grid">
        {books.map((book: any) => (
          <div key={book.id} className="book-card">
            <h3>{book.title}</h3>
            <p>By: {book.author}</p>
            <p>Genre: {book.genre}</p>
            <p>Available copies: {book.copies}</p>
            {user?.role === 'SUBSCRIBER' && book.copies > 0 && (
              <button onClick={() => handleBorrow(book.id)}>
                Borrow Book
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};
```

## Security Considerations

1. **Token Storage**: Store JWT tokens securely (localStorage for web apps, secure storage for mobile)
2. **Token Expiration**: Handle token expiration gracefully (redirect to login)
3. **HTTPS**: Use HTTPS in production
4. **Token Refresh**: Implement token refresh mechanism for better UX
5. **Logout**: Clear tokens on logout
6. **Error Handling**: Handle 401/403 errors appropriately

## Testing

### Test Users
- **Admin**: username: `admin2`, password: `admin123`
- **Subscriber**: username: `subscriber1`, password: `password123`

### Test with curl
```bash
# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"subscriber1","password":"password123"}'

# Use token
curl -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Support

For API-related issues or questions, please refer to the backend team or check the server logs for detailed error information.
