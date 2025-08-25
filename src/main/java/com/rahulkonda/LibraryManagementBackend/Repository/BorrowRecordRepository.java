package com.rahulkonda.LibraryManagementBackend.Repository;

import com.rahulkonda.LibraryManagementBackend.Entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(Long userId);
    List<BorrowRecord> findByUserIdAndIsReturnedFalse(Long userId);
    List<BorrowRecord> findByUserIdAndIsReturnedTrue(Long userId);
    List<BorrowRecord> findByBookId(Long bookId);
    List<BorrowRecord> findByIsReturnedFalse();
    @Query("SELECT br FROM BorrowRecord br WHERE br.isReturned = true AND br.isFinePaid = false AND br.fineAmount > 0")
    List<BorrowRecord> findByIsReturnedTrueAndIsFinePaidFalse();
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.user.id = :userId AND br.isReturned = false")
    List<BorrowRecord> findActiveBorrowsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.user.id = :userId AND br.isReturned = true AND br.isFinePaid = false")
    List<BorrowRecord> findUnpaidFinesByUserId(@Param("userId") Long userId);
}
