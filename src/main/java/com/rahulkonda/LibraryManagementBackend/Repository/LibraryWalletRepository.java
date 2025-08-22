package com.rahulkonda.LibraryManagementBackend.Repository;

import com.rahulkonda.LibraryManagementBackend.Entity.LibraryWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryWalletRepository extends JpaRepository<LibraryWallet, Long> {
    // Since we'll only have one library wallet instance, we can add a method to find it
    default LibraryWallet getLibraryWallet() {
        return findAll().stream().findFirst().orElse(new LibraryWallet());
    }
}
