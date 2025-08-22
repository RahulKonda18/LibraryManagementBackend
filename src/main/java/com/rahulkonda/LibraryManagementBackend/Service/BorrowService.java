package com.rahulkonda.LibraryManagementBackend.Service;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import com.rahulkonda.LibraryManagementBackend.Entity.BorrowRecord;
import com.rahulkonda.LibraryManagementBackend.Entity.LibraryWallet;
import com.rahulkonda.LibraryManagementBackend.Entity.User;
import com.rahulkonda.LibraryManagementBackend.Repository.BorrowRecordRepository;
import com.rahulkonda.LibraryManagementBackend.Repository.LibraryWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryWalletRepository libraryWalletRepository;

    @Transactional
    public BorrowRecord borrowBook(Long userId, Integer bookId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        if (book.getCopies() <= 0) {
            throw new RuntimeException("No copies available for borrowing");
        }

        // Check if user has already borrowed this book
        List<BorrowRecord> activeBorrows = borrowRecordRepository.findActiveBorrowsByUserId(userId);
        boolean alreadyBorrowed = activeBorrows.stream()
                .anyMatch(record -> record.getBook().getId().equals(bookId));
        
        if (alreadyBorrowed) {
            throw new RuntimeException("You have already borrowed this book");
        }

        // Create borrow record
        BorrowRecord borrowRecord = new BorrowRecord(user, book);
        borrowRecord = borrowRecordRepository.save(borrowRecord);

        // Update book copies
        bookService.borrowBook(bookId);

        return borrowRecord;
    }

    @SuppressWarnings("unused")
    @Transactional
    public BorrowRecord returnBook(Long userId, Integer bookId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        // Find the borrow record
        List<BorrowRecord> activeBorrows = borrowRecordRepository.findActiveBorrowsByUserId(userId);
        BorrowRecord borrowRecord = activeBorrows.stream()
                .filter(record -> record.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active borrow record found for this book"));

        // Update borrow record
        borrowRecord.setReturned(true);
        borrowRecord.setReturnDate(LocalDateTime.now());
        borrowRecord.calculateFine();
        
        borrowRecord = borrowRecordRepository.save(borrowRecord);

        // Update book copies
        bookService.returnBook(bookId);

        return borrowRecord;
    }

    @Transactional
    public boolean payFine(Long userId, Long borrowRecordId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (!borrowRecord.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only pay fines for your own borrows");
        }

        if (borrowRecord.isFinePaid()) {
            throw new RuntimeException("Fine has already been paid");
        }

        if (borrowRecord.getFineAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("No fine to pay");
        }

        // Deduct from user's wallet
        boolean deductionSuccess = userService.deductFromWallet(userId, borrowRecord.getFineAmount());
        if (!deductionSuccess) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // Add to library wallet
        LibraryWallet libraryWallet = libraryWalletRepository.getLibraryWallet();
        libraryWallet.addFine(borrowRecord.getFineAmount());
        libraryWalletRepository.save(libraryWallet);

        // Mark fine as paid
        borrowRecord.setFinePaid(true);
        borrowRecordRepository.save(borrowRecord);

        return true;
    }

    public List<BorrowRecord> getUserBorrowHistory(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }

    public List<BorrowRecord> getUserActiveBorrows(Long userId) {
        return borrowRecordRepository.findActiveBorrowsByUserId(userId);
    }

    public List<BorrowRecord> getUserUnpaidFines(Long userId) {
        return borrowRecordRepository.findUnpaidFinesByUserId(userId);
    }

    public List<BorrowRecord> getAllActiveBorrows() {
        return borrowRecordRepository.findByIsReturnedFalse();
    }

    public List<BorrowRecord> getAllUnpaidFines() {
        return borrowRecordRepository.findByIsReturnedTrueAndIsFinePaidFalse();
    }

    public BigDecimal calculateTotalFinesCollected() {
        LibraryWallet libraryWallet = libraryWalletRepository.getLibraryWallet();
        return libraryWallet.getTotalFineCollection();
    }
}
