package com.example.greenkart.greenkart_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Welcome to GreenKart Backend API";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Server is up and running!";
    }
}