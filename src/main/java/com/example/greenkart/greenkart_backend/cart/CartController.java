package com.example.greenkart.greenkart_backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/add_item/{customer_id}/{product_id}")
    public ResponseEntity<String> addItem(@PathVariable("customer_id") String customerId,
                                          @PathVariable("product_id") String productId,
                                          @RequestParam double price,
                                          @RequestParam int qty) {
        service.addItem(customerId, productId, qty, price);
        return ResponseEntity.ok("Item added successfully");
    }

    @DeleteMapping("/delete_item/{item_id}")
    public ResponseEntity<String> deleteItem(@PathVariable("item_id") String itemId) {
        service.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully");
    }
}
