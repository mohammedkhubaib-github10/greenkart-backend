package com.example.greenkart.greenkart_backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    // 🛒 Add an item to the cart
    @PostMapping("/add_item/{customer_id}/{product_id}")
    public ResponseEntity<String> addItem(@PathVariable("customer_id") String customerId,
                                          @PathVariable("product_id") String productId,
                                          @RequestParam double price,
                                          @RequestParam int qty) {
        service.addItem(customerId, productId, qty, price);
        return ResponseEntity.ok("Item added successfully");
    }

    // ❌ Delete an item from the cart
    @DeleteMapping("/delete_item/{item_id}")
    public ResponseEntity<String> deleteItem(@PathVariable("item_id") String itemId) {
        service.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully");
    }

    // 🧾 Get all cart items for a specific customer
    @GetMapping("/get_cart_items/{customer_id}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("customer_id") String customerId) {
        List<CartItem> items = service.getItems(customerId);
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }
}
