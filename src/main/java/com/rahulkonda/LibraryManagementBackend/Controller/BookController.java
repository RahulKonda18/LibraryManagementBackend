package com.rahulkonda.LibraryManagementBackend.Controller;

import com.rahulkonda.LibraryManagementBackend.Entity.Book;
import com.rahulkonda.LibraryManagementBackend.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Page<Book> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/genre")
    public Page<Book> getBooksByGenre(@RequestParam String genre,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getBooksByGenre(genre, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book addedBook = bookService.addBook(book);
            return ResponseEntity.ok(addedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/copies")
    public ResponseEntity<Book> updateBookCopies(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        try {
            Integer newCopies = request.get("copies");
            Book updatedBook = bookService.updateBookCopies(id, newCopies);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/genres")
    public List<String> getGenres() {
        return bookService.getGenresInDB();
    }
    


    @PostMapping("/{id}/borrow")
    public ResponseEntity<Book> borrowBook(@PathVariable Integer id) {
        Book borrowed = bookService.borrowBook(id);
        if (borrowed != null) {
            return ResponseEntity.ok(borrowed);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Book> returnBook(@PathVariable Integer id) {
        Book returned = bookService.returnBook(id);
        if (returned != null) {
            return ResponseEntity.ok(returned);
        }
        return ResponseEntity.badRequest().build();
    }
}
