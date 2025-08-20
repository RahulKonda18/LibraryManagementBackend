package com.rahulkonda.LibraryManagementBackend.Service;


import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import com.rahulkonda.LibraryManagementBackend.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List <Book> getBookByTitle(String title) {
        return bookRepository.findAllByTitleContainingIgnoreCase(title);
    }


    public List<Book> getBooksByCopiesDesc() {
        return bookRepository.getBooksByCopiesDesc();
    }

    public List<Book> getBooksByCopiesAscNative() {
        return bookRepository.getBooksByCopiesAscNative();
    }



    @Transactional()

    public Book addBook(Book book) {
        // By setting the ID to null, we ensure that JPA treats this as a new entity
        // for insertion. This is the idiomatic way when using wrapper types for IDs.
        book.setId(null);
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Integer id, Book bookDetails) {
        // The "fetch-then-update" pattern is the correct way to handle updates with JPA.
        // 1. Fetch the existing entity from the database to get a "managed" entity.
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            // 2. Get the managed entity that Hibernate is aware of.
            Book existingBook = optionalBook.get();

            // 3. Update its properties with the new data from the request body.
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setCopies(bookDetails.getCopies());

            // 4. Save the updated entity. Because existingBook is a managed entity,
            //    Hibernate knows how to correctly generate an UPDATE statement.
            return bookRepository.save(existingBook);
        }
        return null; // Return null if the book to update is not found
    }

    public boolean deleteBook(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
