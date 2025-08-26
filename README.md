# Library Management System

A comprehensive library management system built with Spring Boot that supports two types of users: **Admins** and **Subscribers**. The system features JWT authentication, role-based access control, wallet management, and automated fine calculation.

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Quick Start](#-quick-start)
- [Authentication & Security](#-authentication--security)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Business Rules](#-business-rules)
- [Frontend Integration](#-frontend-integration)
- [Testing](#-testing)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [Contributing](#-contributing)

## ✨ Features

### Admin Features
- 🔐 Secure JWT-based authentication
- 📚 Add, delete, and modify books
- 📊 Update the number of book copies
- 💰 View total fine collection from subscribers
- 👥 Manage all users and their borrowing records
- 📈 Monitor active borrows and unpaid fines
- 🎯 Role-based access control

### Subscriber Features
- 📖 Borrow and return books
- 📋 View borrowing history and active borrows
- 💳 Check wallet balance and fine status
- 💸 Pay fines from wallet
- 📚 View list of borrowed books
- 🔐 Secure authentication with JWT tokens

### System Features
- 🔒 JWT-based authentication and authorization
- 👤 Role-based access control (Admin/Subscriber)
- 💰 Wallet system with initial balance of ₹200
- ⏰ Automatic fine calculation (₹5 per day for overdue books)
- 📅 14-day borrowing period
- 💸 Fine collection and transfer to library wallet
- 🌐 CORS support for frontend integration
- 🔐 Password encryption using BCrypt

## 🛠 Technology Stack

- **Backend Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Database**: H2 (in-memory for development)
- **Build Tool**: Gradle
- **Authentication**: JWT (JSON Web Token)
- **Security**: Spring Security with BCrypt
- **API Documentation**: RESTful APIs with JSON
- **CORS**: Enabled for frontend integration

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Gradle (or use the included wrapper)

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/RahulKonda18/LibraryManagementBackend.git
   cd LibraryManagementBackend
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the application**
   - Application URL: `http://localhost:8080`
   - Database will be automatically created with initial data

### Initial Test Data

#### Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ADMIN`

#### Subscriber Users
- **Username**: `john_doe` | **Password**: `password123`
- **Username**: `jane_smith` | **Password**: `password123`
- **Username**: `bob_wilson` | **Password**: `password123`

#### Initial Wallet Balance
- All subscribers start with ₹200.00 in their wallet
- Library wallet starts with ₹0.00

## 🔐 Authentication & Security

### JWT Authentication Flow

1. **Register** a new user (optional - for new users)
2. **Login** to get a JWT token
3. **Include the token** in all subsequent API requests

### Headers Format
```
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

### Public Endpoints (No Authentication Required)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login
- `GET /api/health/**` - Health check endpoints

### Protected Endpoints

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

### JWT Token Structure
The JWT token contains:
- **Subject**: Username
- **Role**: User role (ADMIN/SUBSCRIBER)
- **UserId**: User ID
- **Issued At**: Token creation time
- **Expiration**: Token expiration time (24 hours by default)

### Security Features
1. **Password Encryption**: All passwords are encrypted using BCrypt
2. **Token Expiration**: JWT tokens expire after 24 hours
3. **Role-Based Access Control**: Different endpoints require different user roles
4. **Stateless Authentication**: No server-side session storage
5. **CORS Support**: Configured for frontend integration

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### 1. Authentication Endpoints

#### Register User
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "name": "New User",
  "email": "newuser@example.com",
  "role": "SUBSCRIBER"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "newuser",
  "name": "New User",
  "email": "newuser@example.com",
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
  "username": "john_doe",
  "password": "password123"
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

### 2. User Management

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

### 3. Wallet Management

#### Get Wallet Balance
```http
GET /api/users/{id}/wallet
Authorization: Bearer <token>
```

#### Get Total Fines Paid
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

### 4. Book Management

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
  "totalPages": 20,
  "totalElements": 173,
  "size": 9,
  "number": 0
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

### 5. Borrowing Operations

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

### 6. Admin Operations

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

### Error Responses

#### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or missing JWT token"
}
```

#### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "Insufficient permissions"
}
```

#### 400 Bad Request
```json
{
  "error": "Bad Request",
  "message": "Invalid request format"
}
```

#### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found"
}
```

## 🗄 Database Schema

### Users Table
- `id`: Primary key
- `username`: Unique username
- `password`: Encrypted user password (BCrypt)
- `name`: Full name
- `email`: Email address
- `role`: ADMIN or SUBSCRIBER
- `wallet_balance`: Current wallet balance (initial ₹200)
- `total_fines_paid`: Total fines paid by user

### Books Table
- `id`: Primary key
- `title`: Book title
- `author`: Book author
- `published_year`: Publication year
- `genre`: Book genre
- `copies`: Number of available copies

### Borrow Records Table
- `id`: Primary key
- `user_id`: Foreign key to users table
- `book_id`: Foreign key to books table
- `borrow_date`: Date when book was borrowed
- `return_date`: Date when book was returned
- `due_date`: Due date (14 days from borrow date)
- `fine_amount`: Calculated fine amount
- `is_returned`: Whether book has been returned
- `is_fine_paid`: Whether fine has been paid

### Library Wallet Table
- `id`: Primary key
- `total_fine_collection`: Total fines collected by library

## 📋 Business Rules

1. **Borrowing Period**: 14 days
2. **Fine Rate**: ₹5 per day for overdue books
3. **Initial Wallet Balance**: ₹200 for new subscribers
4. **Fine Payment**: Automatically deducted from user's wallet
5. **Book Copies**: Cannot borrow if no copies available
6. **Duplicate Borrows**: Cannot borrow the same book twice
7. **Role-based Access**: Admin can manage books, subscribers can borrow/return
8. **JWT Token Expiration**: 24 hours
9. **Password Security**: All passwords encrypted with BCrypt

## 🌐 Frontend Integration

### JavaScript/TypeScript Integration

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

### Security Considerations

1. **Token Storage**: Store JWT tokens securely (localStorage for web apps, secure storage for mobile)
2. **Token Expiration**: Handle token expiration gracefully (redirect to login)
3. **HTTPS**: Use HTTPS in production
4. **Token Refresh**: Implement token refresh mechanism for better UX
5. **Logout**: Clear tokens on logout
6. **Error Handling**: Handle 401/403 errors appropriately

## 🧪 Testing

### Test Users
- **Admin**: username: `admin`, password: `admin123`
- **Subscriber**: username: `john_doe`, password: `password123`

### Test with curl
```bash
# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"password123"}'

# Use token
curl -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### API Testing
You can test all endpoints using tools like:
- **Postman**: Import the API collection
- **curl**: Command-line testing
- **Frontend Application**: React, Angular, Vue.js, etc.

## ⚙️ Configuration

### JWT Configuration
JWT settings can be configured in `application.properties`:

```properties
# JWT Configuration
jwt.secret=yourSecretKeyHereMakeItLongAndSecureForProductionUse
jwt.expiration=86400000
```

### Database Configuration
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### CORS Configuration
```properties
# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

## 🚀 Deployment

### Production Considerations

1. **Database**: Replace H2 with PostgreSQL, MySQL, or other production database
2. **Security**: 
   - Use strong JWT secret
   - Enable HTTPS
   - Configure proper CORS settings
   - Implement rate limiting
3. **Environment Variables**: Use environment variables for sensitive configuration
4. **Logging**: Configure proper logging levels
5. **Monitoring**: Add health checks and monitoring

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Environment Variables
```bash
# JWT Configuration
JWT_SECRET=your-production-secret-key
JWT_EXPIRATION=86400000

# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/library_db
DB_USERNAME=library_user
DB_PASSWORD=secure_password

# Server Configuration
SERVER_PORT=8080
```

## 📊 Initial Data

The system comes with:
- **100+ books** from various genres (Fiction, Fantasy/Sci-Fi, Thriller/Mystery, Children's Literature, Non-Fiction, Romance/Drama)
- **1 admin user** (username: `admin`, password: `admin123`)
- **3 subscriber users** (usernames: `john_doe`, `jane_smith`, `bob_wilson`, password: `password123`)
- **Initial library wallet** with ₹0 balance

## 🔄 Fine Calculation

- **Borrowing period**: 14 days
- **Fine rate**: ₹5 per day for overdue books
- **Automatic calculation**: Fines are automatically calculated when books are returned
- **Payment**: Fines are deducted from user's wallet and transferred to library wallet

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java coding conventions
- Add proper documentation
- Write unit tests for new features
- Update API documentation when adding new endpoints

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- Create an issue in the GitHub repository
- Check the API documentation
- Review the server logs for detailed error information

## 🔮 Future Enhancements

- [ ] JWT token refresh mechanism
- [ ] Email notifications for due dates
- [ ] Book reservations system
- [ ] Advanced search and filtering
- [ ] Reports and analytics dashboard
- [ ] Mobile app support
- [ ] Multi-language support
- [ ] Book reviews and ratings
- [ ] Integration with external book APIs
- [ ] Automated backup system

---

**Note**: This is a development version with basic authentication. For production use, ensure proper security measures are implemented including HTTPS, secure JWT secrets, and proper error handling.
