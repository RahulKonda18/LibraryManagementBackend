package com.rahulkonda.LibraryManagementBackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "library_wallet")
public class LibraryWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalFineCollection;
    
    public LibraryWallet() {
        this.totalFineCollection = BigDecimal.ZERO;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getTotalFineCollection() {
        return totalFineCollection;
    }
    
    public void setTotalFineCollection(BigDecimal totalFineCollection) {
        this.totalFineCollection = totalFineCollection;
    }
    
    public void addFine(BigDecimal fineAmount) {
        this.totalFineCollection = this.totalFineCollection.add(fineAmount);
    }
}
