package com.rahulkonda.LibraryManagementBackend.Controller;

import com.rahulkonda.LibraryManagementBackend.Entity.BorrowRecord;
import com.rahulkonda.LibraryManagementBackend.Entity.User;
import com.rahulkonda.LibraryManagementBackend.Service.BorrowService;
import com.rahulkonda.LibraryManagementBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @GetMapping("/total-fines")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getTotalFinesCollected() {
        return ResponseEntity.ok(borrowService.calculateTotalFinesCollected());
    }

    @GetMapping("/active-borrows")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowRecord>> getAllActiveBorrows() {
        return ResponseEntity.ok(borrowService.getAllActiveBorrows());
    }

    @GetMapping("/unpaid-fines")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowRecord>> getAllUnpaidFines() {
        return ResponseEntity.ok(borrowService.getAllUnpaidFines());
    }

    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllSubscribers() {
        return ResponseEntity.ok(userService.getUsersByRole(User.UserRole.SUBSCRIBER));
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userService.getUsersByRole(User.UserRole.ADMIN));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
