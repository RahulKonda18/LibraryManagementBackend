package com.rahulkonda.LibraryManagementBackend.Controller;

import com.rahulkonda.LibraryManagementBackend.Entity.LibraryItem;
import com.rahulkonda.LibraryManagementBackend.Service.LibraryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-items")
public class LibraryItemController {

    @Autowired
    private LibraryItemService libraryItemService;

    @GetMapping
    public List<LibraryItem> getAllLibraryItems() {
        return libraryItemService.getAllLibraryItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getLibraryItem(@PathVariable Integer id) {
        LibraryItem libraryItem = libraryItemService.getLibraryItem(id);
        if (libraryItem != null) {
            return ResponseEntity.ok(libraryItem);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public LibraryItem addLibraryItem(@RequestBody LibraryItem libraryItem) {
        return libraryItemService.addLibraryItem(libraryItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryItem> updateLibraryItem(@PathVariable Integer id, @RequestBody LibraryItem libraryItemDetails) {
        LibraryItem updatedLibraryItem = libraryItemService.updateLibraryItem(id, libraryItemDetails);
        if (updatedLibraryItem != null) {
            return ResponseEntity.ok(updatedLibraryItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibraryItem(@PathVariable Integer id) {
        if (libraryItemService.deleteLibraryItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}