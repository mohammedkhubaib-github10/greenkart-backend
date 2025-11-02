package com.example.greenkart.greenkart_backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getString("product_image"),
                rs.getDouble("price"),
                rs.getString("vendor_id"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    public void save(Product product) {
        String sql = "INSERT INTO Products (product_id, product_name, product_image, price, vendor_id, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getProductId(),
                product.getProductName(),
                product.getProductImage(),
                product.getPrice(),
                product.getVendorId(),
                product.getCreatedAt());
    }

    public List<Product> findByVendor(String vendorId) {
        String sql = "SELECT * FROM Products WHERE vendor_id = ?";
        return jdbcTemplate.query(sql, this::mapRow, vendorId);
    }

    public Optional<Product> findById(String productId) {
        String sql = "SELECT * FROM Products WHERE product_id = ?";
        List<Product> list = jdbcTemplate.query(sql, this::mapRow, productId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM Products", this::mapRow);
    }

    public int update(Product product) {
        String sql = "UPDATE Products SET product_name=?, product_image=?, price=? WHERE product_id=? AND vendor_id=?";
        return jdbcTemplate.update(sql,
                product.getProductName(),
                product.getProductImage(),
                product.getPrice(),
                product.getProductId(),
                product.getVendorId());
    }

    public int delete(String productId, String vendorId) {
        String sql = "DELETE FROM Products WHERE product_id=? AND vendor_id=?";
        return jdbcTemplate.update(sql, productId, vendorId);
    }
}
