package com.example.greenkart.greenkart_backend.vendor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Vendors")
public class Vendor {

    @Id
    @Column(name = "vendor_id", nullable = false, updatable = false)
    private String vendorId = UUID.randomUUID().toString();

    @Column(name = "vendor_name", length = 100)
    private String vendorName;

    @Column(name = "brand_name", unique = true, length = 100)
    private String brandName;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 10)
    private String contact;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relationships — add later when Product and Address exist
    // @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Product> products;

    // @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<VendorAddress> shopAddresses;

    // ----- Getters & Setters -----
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
