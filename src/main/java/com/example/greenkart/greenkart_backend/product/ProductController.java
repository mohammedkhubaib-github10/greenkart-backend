package com.example.greenkart.greenkart_backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/add_product/{vendor_id}")
    public ResponseEntity<Product> addProduct(@PathVariable("vendor_id") String vendorId, @RequestBody Product product) {
        return ResponseEntity.ok(service.addProduct(vendorId, product));
    }

    @GetMapping("/get_product/{vendor_id}")
    public List<Product> getProductsByVendor(@PathVariable("vendor_id") String vendorId) {
        return service.getProductsByVendor(vendorId);
    }

    @GetMapping("/get_all_product")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PutMapping("/update_product/{product_id}/{vendor_id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("product_id") String productId,
            @PathVariable("vendor_id") String vendorId,
            @RequestBody Product product) {
        service.updateProduct(vendorId, productId, product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/delete_product/{product_id}/{vendor_id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("product_id") String productId,
            @PathVariable("vendor_id") String vendorId) {
        service.deleteProduct(vendorId, productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
