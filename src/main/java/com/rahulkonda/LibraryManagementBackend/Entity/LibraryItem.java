package com.rahulkonda.LibraryManagementBackend.Entity;

import com.rahulkonda.LibraryManagementBackend.LibraryItemType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LibraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @Enumerated(EnumType.STRING)
    private LibraryItemType type;

    public LibraryItem() {
    }

    public LibraryItem(Integer id, String title, LibraryItemType type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LibraryItemType getType() {
        return type;
    }

    public void setType(LibraryItemType type) {
        this.type = type;
    }
}
