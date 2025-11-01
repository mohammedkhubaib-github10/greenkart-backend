package com.example.greenkart.greenkart_backend.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private String customerId;
    private String customerName;
    private String email;
    private String contact;
    private LocalDateTime createdAt;

    // Constructor for new customers (auto-generate id & timestamp)
    public Customer() {
        this.customerId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for existing customers (when reading from DB)
    public Customer(String customerId, String customerName, String email, String contact, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.contact = contact;
        this.createdAt = createdAt;
    }

    // Getters (we can keep setters for flexibility)
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
