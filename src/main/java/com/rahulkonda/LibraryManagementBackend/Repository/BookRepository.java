package com.rahulkonda.LibraryManagementBackend.Repository;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // DQM  (Derived Query Methods)
    List<Book> findAllByTitleContainingIgnoreCase(String title);


    // @Query Annotation (JPQL)
    @Query("SELECT b FROM Book b ORDER BY b.copies DESC")
    List<Book> getBooksByCopiesDesc(); 


    //@Query Annotation Native SQL 
    @Query(value = "SELECT * FROM Book ORDER BY copies ASC", nativeQuery = true)
    List<Book> getBooksByCopiesAscNative();

    


}
