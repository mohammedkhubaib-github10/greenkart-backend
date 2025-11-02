package com.example.greenkart.greenkart_backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product addProduct(String vendorId, Product product) {
        product.setProductId(UUID.randomUUID().toString());
        product.setVendorId(vendorId);
        product.setCreatedAt(LocalDateTime.now());
        repo.save(product);
        return product;
    }

    public List<Product> getProductsByVendor(String vendorId) {
        return repo.findByVendor(vendorId);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public void updateProduct(String vendorId, String productId, Product product) {
        product.setVendorId(vendorId);
        product.setProductId(productId);
        repo.update(product);
    }

    public void deleteProduct(String vendorId, String productId) {
        repo.delete(productId, vendorId);
    }
}
