package com.rahulkonda.LibraryManagementBackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(nullable = false)
    private LocalDateTime borrowDate;
    
    private LocalDateTime returnDate;
    
    @Column(nullable = false)
    private LocalDateTime dueDate;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal fineAmount;
    
    @Column(nullable = false)
    private boolean isReturned;
    
    @Column(nullable = false)
    private boolean isFinePaid;
    
    public BorrowRecord() {
        this.borrowDate = LocalDateTime.now();
        this.dueDate = this.borrowDate.plusDays(14); // 2 weeks borrowing period
        this.isReturned = false;
        this.isFinePaid = false;
        this.fineAmount = BigDecimal.ZERO;
    }
    
    public BorrowRecord(User user, Book book) {
        this();
        this.user = user;
        this.book = book;
    }
    
    // Calculate fine based on overdue days
    public void calculateFine() {
        if (isReturned && returnDate != null && returnDate.isAfter(dueDate)) {
            long overdueDays = java.time.Duration.between(dueDate, returnDate).toDays();
            this.fineAmount = BigDecimal.valueOf(overdueDays * 5.0); // 5 rupees per day
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public BigDecimal getFineAmount() {
        return fineAmount;
    }
    
    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }
    
    public boolean isReturned() {
        return isReturned;
    }
    
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
    
    public boolean isFinePaid() {
        return isFinePaid;
    }
    
    public void setFinePaid(boolean finePaid) {
        isFinePaid = finePaid;
    }
}
