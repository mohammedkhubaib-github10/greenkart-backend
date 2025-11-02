package com.example.greenkart.greenkart_backend.product;

import java.time.LocalDateTime;

public class Product {
    private String productId;
    private String productName;
    private String productImage;
    private double price;
    private String vendorId;
    private LocalDateTime createdAt;

    public Product() {}

    public Product(String productId, String productName, String productImage, double price, String vendorId, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.vendorId = vendorId;
        this.createdAt = createdAt;
    }

    // Getters & setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
