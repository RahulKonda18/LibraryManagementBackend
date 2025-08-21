package com.rahulkonda.LibraryManagementBackend.Repository;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT DISTINCT b.genre FROM Book b")
    List<String> findAllUniqueGenres();
    
    Page<Book> findByGenreIgnoreCase(String genre, Pageable pageable);
    
}