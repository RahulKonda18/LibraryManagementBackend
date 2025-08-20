package com.rahulkonda.LibraryManagementBackend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer copies;
    
    public Book() {
        // No-argument constructor for JSON deserialization
    }

    public Book(Integer id, String title, Integer copies) {
        this.id = id;
        this.title = title;
        this.copies = copies;
    }

    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Integer getCopies() {
        return copies;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCopies(Integer copies) {
        this.copies = copies;
    }

}
