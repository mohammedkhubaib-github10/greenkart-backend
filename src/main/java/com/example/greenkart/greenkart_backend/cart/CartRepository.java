package com.example.greenkart.greenkart_backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItem mapItem(ResultSet rs, int rowNum) throws SQLException {
        CartItem item = new CartItem();
        item.setItemId(rs.getString("item_id"));
        item.setCartId(rs.getString("cart_id"));
        item.setProductId(rs.getString("product_id"));
        item.setQty(rs.getInt("qty"));
        item.setTotalPrice(rs.getDouble("total_price"));
        return item;
    }

    public void createCart(String customerId) {
        String sql = "INSERT INTO Carts (cart_id, customer_id, amount) VALUES (?, ?, 0)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), customerId);
    }

    public Optional<Cart> findCartByCustomer(String customerId) {
        String sql = "SELECT * FROM Carts WHERE customer_id = ?";
        List<Cart> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cart c = new Cart();
            c.setCartId(rs.getString("cart_id"));
            c.setCustomerId(rs.getString("customer_id"));
            c.setAmount(rs.getDouble("amount"));
            return c;
        }, customerId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public void addItem(String cartId, String productId, int qty, double totalPrice) {
        String sql = "INSERT INTO Cart_Items (item_id, cart_id, product_id, qty, total_price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), cartId, productId, qty, totalPrice);
    }

    public List<CartItem> getItems(String cartId) {
        String sql = "SELECT * FROM Cart_Items WHERE cart_id = ?";
        return jdbcTemplate.query(sql, this::mapItem, cartId);
    }

    public void updateCartAmount(String cartId, double amount) {
        String sql = "UPDATE Carts SET amount=? WHERE cart_id=?";
        jdbcTemplate.update(sql, amount, cartId);
    }

    public void deleteItem(String itemId) {
        String sql = "DELETE FROM Cart_Items WHERE item_id=?";
        jdbcTemplate.update(sql, itemId);
    }

    // Vendor validation helpers
    public Optional<String> findVendorInCart(String cartId) {
        String sql = """
            SELECT p.vendor_id
            FROM Cart_Items ci
            JOIN Products p ON ci.product_id = p.product_id
            WHERE ci.cart_id = ?
            LIMIT 1
        """;
        List<String> list = jdbcTemplate.queryForList(sql, String.class, cartId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<String> findVendorOfProduct(String productId) {
        String sql = "SELECT vendor_id FROM Products WHERE product_id = ?";
        List<String> list = jdbcTemplate.queryForList(sql, String.class, productId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
