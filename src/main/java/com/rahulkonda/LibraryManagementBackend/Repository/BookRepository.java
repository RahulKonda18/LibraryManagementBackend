package com.rahulkonda.LibraryManagementBackend.Repository;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}