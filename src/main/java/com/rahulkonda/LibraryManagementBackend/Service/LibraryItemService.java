package com.rahulkonda.LibraryManagementBackend.Service;

import com.rahulkonda.LibraryManagementBackend.Entity.LibraryItem;
import com.rahulkonda.LibraryManagementBackend.Repository.LibraryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryItemService {

    @Autowired
    private LibraryItemRepository libraryItemRepository;

    public List<LibraryItem> getAllLibraryItems() {
        return libraryItemRepository.findAll();
    }

    public LibraryItem getLibraryItem(Integer id) {
        return libraryItemRepository.findById(id).orElse(null);
    }

    @Transactional
    public LibraryItem addLibraryItem(LibraryItem libraryItem) {
        libraryItem.setId(null);
        return libraryItemRepository.save(libraryItem);
    }

    @Transactional
    public LibraryItem updateLibraryItem(Integer id, LibraryItem libraryItemDetails) {
        Optional<LibraryItem> optionalLibraryItem = libraryItemRepository.findById(id);

        if (optionalLibraryItem.isPresent()) {
            LibraryItem existingLibraryItem = optionalLibraryItem.get();
            existingLibraryItem.setTitle(libraryItemDetails.getTitle());
            existingLibraryItem.setType(libraryItemDetails.getType());
            return libraryItemRepository.save(existingLibraryItem);
        }
        return null;
    }

    public boolean deleteLibraryItem(Integer id) {
        if (libraryItemRepository.existsById(id)) {
            libraryItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}