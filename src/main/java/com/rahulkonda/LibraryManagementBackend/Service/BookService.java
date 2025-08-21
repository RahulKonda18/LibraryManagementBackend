package com.rahulkonda.LibraryManagementBackend.Service;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import com.rahulkonda.LibraryManagementBackend.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired 
    private BookRepository bookRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> getBooksByGenre(String genre, Pageable pageable) {
        return bookRepository.findByGenreIgnoreCase(genre, pageable);
    }

    public Book getBook(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    // @Transactional
    // public Book addBook(Book book) {
    //     book.setId(null);
    //     if (book.getCopies() == null) {
    //         book.setCopies(1);
    //     }
    //     return bookRepository.save(book);
    // }

    @Transactional
    public Book borrowBook(Integer id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }

        Integer currentCopies = book.getCopies() == null ? 1 : book.getCopies();
        if (currentCopies <= 0) {
            return null;
        }

        book.setCopies(currentCopies - 1);
        return bookRepository.save(book);
    }

    @Transactional
    public Book returnBook(Integer id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }

        Integer currentCopies = book.getCopies() == null ? 1 : book.getCopies();
        book.setCopies(currentCopies + 1);
        return bookRepository.save(book);
    }


    public List<String> getGenresInDB(){
        return bookRepository.findAllUniqueGenres();
    }
}
