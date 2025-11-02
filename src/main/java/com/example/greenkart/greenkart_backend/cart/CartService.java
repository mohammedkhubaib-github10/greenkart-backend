package com.example.greenkart.greenkart_backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository repo;

    public void createCartIfNotExists(String customerId) {
        Optional<Cart> existing = repo.findCartByCustomer(customerId);
        if (existing.isEmpty()) {
            repo.createCart(customerId);
        }
    }

    public List<CartItem> getItems(String cartId) {
        return repo.getItems(cartId);
    }

    public void addItem(String customerId, String productId, int qty, double pricePerUnit) {
        createCartIfNotExists(customerId);

        Cart cart = repo.findCartByCustomer(customerId).orElseThrow(() -> new RuntimeException("Cart not found"));
        String cartId = cart.getCartId();

        // Vendor constraint check
        Optional<String> existingVendor = repo.findVendorInCart(cartId);
        Optional<String> newVendor = repo.findVendorOfProduct(productId);

        if (newVendor.isEmpty())
            throw new RuntimeException("Product not found");

        if (existingVendor.isPresent() && !existingVendor.get().equals(newVendor.get()))
            throw new RuntimeException("Cannot add products from multiple vendors in the same cart");

        double total = qty * pricePerUnit;
        repo.addItem(cartId, productId, qty, total);

        List<CartItem> allItems = repo.getItems(cartId);
        double newTotal = allItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        repo.updateCartAmount(cartId, newTotal);
    }

    public void deleteItem(String itemId) {
        repo.deleteItem(itemId);
    }
}
