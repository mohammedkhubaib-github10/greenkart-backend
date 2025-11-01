package com.example.greenkart.greenkart_backend.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private OtpService otpService;

    // ----- OTP -----
    public String requestOtp(String contact) {
        return "OTP sent successfully: " + otpService.generateOtp(contact);
    }

    public String verifyOtp(String contact, String enteredOtp) {
        boolean verified = otpService.verifyOtp(contact, enteredOtp);
        if (!verified)
            throw new RuntimeException("Invalid OTP or expired");

        // If verified, ensure customer exists
        Optional<Customer> existing = repo.findByContact(contact);
        if (existing.isEmpty()) {
            Customer newCustomer = new Customer();
            newCustomer.setContact(contact);
            newCustomer.setCreatedAt(LocalDateTime.now());
            repo.save(newCustomer);
        }

        return "Logged in successfully";
    }

    // ----- CRUD -----
    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void update(String id, Customer updated) {
        updated.setCustomerId(id);
        repo.update(updated);
    }

    public void delete(String id) {
        repo.delete(id);
    }
}
