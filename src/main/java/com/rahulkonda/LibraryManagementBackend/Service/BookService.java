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

    @Transactional
    public Book addBook(Book book) {
        book.setId(null);
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Integer id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public boolean deleteBook(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
