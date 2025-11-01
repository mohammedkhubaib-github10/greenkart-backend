package com.example.greenkart.greenkart_backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }
    
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
    
    public Cart addItemToCart(Long cartId, CartItem item) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }
    
    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        return cartRepository.save(cart);
    }
}