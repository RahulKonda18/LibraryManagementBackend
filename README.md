# Library Management System

A comprehensive library management system built with Spring Boot that supports two types of users: **Admins** and **Subscribers**.

## Features

### Admin Features
- Add, delete, and modify books
- Update the number of book copies
- View total fine collection from subscribers
- Manage all users and their borrowing records
- Monitor active borrows and unpaid fines

### Subscriber Features
- Borrow and return books
- View borrowing history and active borrows
- Check wallet balance and fine status
- Pay fines from wallet
- View list of borrowed books

### System Features
- Role-based access control (Admin/Subscriber)
- Wallet system with initial balance of ₹200
- Automatic fine calculation (₹5 per day for overdue books)
- 14-day borrowing period
- Fine collection and transfer to library wallet

## Database Schema

### Users Table
- `id`: Primary key
- `username`: Unique username
- `password`: User password
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

## API Endpoints

### Authentication & User Management

#### Register User
```
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

#### Login User
```
POST /api/users/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

#### Get All Users
```
GET /api/users
```

#### Get Users by Role
```
GET /api/users/role/{role}
```
Roles: `ADMIN`, `SUBSCRIBER`

#### Get User by ID
```
GET /api/users/{id}
```

#### Update User
```
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "email": "updated@example.com",
  "password": "newpassword"
}
```

#### Delete User
```
DELETE /api/users/{id}
```

### Wallet Management

#### Get Wallet Balance
```
GET /api/users/{id}/wallet
```

#### Get Total Fines Paid
```
GET /api/users/{id}/fines
```

#### Add Money to Wallet
```
POST /api/users/{id}/wallet/add
Content-Type: application/json

{
  "amount": 50.00
}
```

### Book Management (Admin Only)

#### Get All Books
```
GET /api/books?page=0&size=9
```

#### Get Books by Genre
```
GET /api/books/genre?genre=Fiction&page=0&size=9
```

#### Get Book by ID
```
GET /api/books/{id}
```

#### Add New Book
```
POST /api/books
Content-Type: application/json

{
  "title": "New Book Title",
  "author": "Author Name",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 5
}
```

#### Update Book
```
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "author": "Updated Author",
  "publishedYear": 2023,
  "genre": "Fiction",
  "copies": 10
}
```

#### Delete Book
```
DELETE /api/books/{id}
```

#### Update Book Copies
```
PUT /api/books/{id}/copies
Content-Type: application/json

{
  "copies": 15
}
```

#### Get All Genres
```
GET /api/books/genres
```

### Borrowing Operations

#### Borrow Book
```
POST /api/borrows/{userId}/books/{bookId}/borrow
```

#### Return Book
```
POST /api/borrows/{userId}/books/{bookId}/return
```

#### Pay Fine
```
POST /api/borrows/{userId}/fines/{borrowRecordId}/pay
```

#### Get User Borrow History
```
GET /api/borrows/{userId}/history
```

#### Get User Active Borrows
```
GET /api/borrows/{userId}/active
```

#### Get User Unpaid Fines
```
GET /api/borrows/{userId}/unpaid-fines
```

### Admin Operations

#### Get Total Fines Collected
```
GET /api/admin/total-fines
```

#### Get All Active Borrows
```
GET /api/admin/active-borrows
```

#### Get All Unpaid Fines
```
GET /api/admin/unpaid-fines
```

#### Get All Subscribers
```
GET /api/admin/subscribers
```

#### Get All Admins
```
GET /api/admin/admins
```

#### Get All Users
```
GET /api/admin/users
```

## Initial Data

The system comes with:
- 100+ books from various genres
- 1 admin user (username: `admin`, password: `admin123`)
- 3 subscriber users (usernames: `john_doe`, `jane_smith`, `bob_wilson`, password: `password123`)
- Initial library wallet with ₹0 balance

## Fine Calculation

- Borrowing period: 14 days
- Fine rate: ₹5 per day for overdue books
- Fines are automatically calculated when books are returned
- Fines are deducted from user's wallet and transferred to library wallet

## Running the Application

1. Ensure you have Java 17+ and Gradle installed
2. Clone the repository
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
4. The application will start on `http://localhost:8080`
5. Database will be automatically created with initial data

## Technology Stack

- **Backend**: Spring Boot 3.x
- **Database**: H2 (in-memory for development)
- **Build Tool**: Gradle
- **Language**: Java 17+

## Security Notes

- This is a development version with basic authentication
- Passwords are stored in plain text (should be encrypted in production)
- No JWT or session management implemented
- CORS is enabled for localhost:3000

## Future Enhancements

- JWT-based authentication
- Password encryption
- Email notifications
- Book reservations
- Advanced search and filtering
- Reports and analytics
- Mobile app support
