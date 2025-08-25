package com.rahulkonda.LibraryManagementBackend.Controller;

import com.rahulkonda.LibraryManagementBackend.Entity.BorrowRecord;
import com.rahulkonda.LibraryManagementBackend.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/borrows")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/{userId}/books/{bookId}/borrow")
    @PreAuthorize("hasRole('SUBSCRIBER')")
    public ResponseEntity<BorrowRecord> borrowBook(@PathVariable Long userId, @PathVariable Integer bookId) {
        try {
            BorrowRecord borrowRecord = borrowService.borrowBook(userId, bookId);
            return ResponseEntity.ok(borrowRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{userId}/books/{bookId}/return")
    @PreAuthorize("hasRole('SUBSCRIBER')")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long userId, @PathVariable Integer bookId) {
        try {
            BorrowRecord borrowRecord = borrowService.returnBook(userId, bookId);
            return ResponseEntity.ok(borrowRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{userId}/fines/{borrowRecordId}/pay")
    @PreAuthorize("hasRole('SUBSCRIBER')")
    public ResponseEntity<Boolean> payFine(@PathVariable Long userId, @PathVariable Long borrowRecordId) {
        try {
            boolean success = borrowService.payFine(userId, borrowRecordId);
            return ResponseEntity.ok(success);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<BorrowRecord>> getUserBorrowHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserBorrowHistory(userId));
    }

    @GetMapping("/{userId}/active")
    public ResponseEntity<List<BorrowRecord>> getUserActiveBorrows(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserActiveBorrows(userId));
    }

    @GetMapping("/{userId}/unpaid-fines")
    public ResponseEntity<List<BorrowRecord>> getUserUnpaidFines(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserUnpaidFines(userId));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowRecord>> getAllActiveBorrows() {
        return ResponseEntity.ok(borrowService.getAllActiveBorrows());
    }

    @GetMapping("/unpaid-fines")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowRecord>> getAllUnpaidFines() {
        return ResponseEntity.ok(borrowService.getAllUnpaidFines());
    }

    @GetMapping("/total-fines")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getTotalFinesCollected() {
        return ResponseEntity.ok(borrowService.calculateTotalFinesCollected());
    }
}
