package com.example.greenkart.greenkart_backend.vendor;

import com.example.greenkart.greenkart_backend.customer.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VendorService {

    @Autowired
    private VendorRepository repo;

    @Autowired
    private OtpService otpService;

    // ---- OTP ----
    public String requestOtp(String contact) {
        return "OTP sent successfully: " + otpService.generateOtp(contact);
    }

    public String verifyOtp(String contact, String enteredOtp) {
        boolean verified = otpService.verifyOtp(contact, enteredOtp);
        if (!verified)
            throw new RuntimeException("Invalid OTP or expired");

        // if verified, ensure vendor exists
        Optional<Vendor> existing = repo.findByContact(contact);
        if (existing.isEmpty()) {
            Vendor newVendor = new Vendor();
            newVendor.setVendorId(UUID.randomUUID().toString());
            newVendor.setContact(contact);
            newVendor.setCreatedAt(LocalDateTime.now());
            repo.save(newVendor);
        }

        return "Logged in successfully";
    }

    // ---- CRUD ----
    public List<Vendor> getAll() {
        return repo.findAll();
    }

    public Vendor getById(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public void update(String id, Vendor updated) {
        updated.setVendorId(id);
        repo.update(updated);
    }

    public void delete(String id) {
        repo.delete(id);
    }
}
