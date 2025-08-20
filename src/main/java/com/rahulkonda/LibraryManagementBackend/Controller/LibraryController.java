
package com.rahulkonda.LibraryManagementBackend.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/")
public class LibraryController {
    
    @GetMapping("/hi")
    public String getDummy() {
        return "Hello, World! From Spring Boot REST API!";
    }
}
