package com.example.greenkart.greenkart_backend.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class VendorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setVendorId(rs.getString("vendor_id"));
        vendor.setVendorName(rs.getString("vendor_name"));
        vendor.setBrandName(rs.getString("brand_name"));
        vendor.setEmail(rs.getString("email"));
        vendor.setContact(rs.getString("contact"));
        vendor.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return vendor;
    }

    public void save(Vendor vendor) {
        String sql = "INSERT INTO Vendors (vendor_id, vendor_name, brand_name, email, contact, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                vendor.getVendorId(),
                vendor.getVendorName(),
                vendor.getBrandName(),
                vendor.getEmail(),
                vendor.getContact(),
                vendor.getCreatedAt());
    }

    public Optional<Vendor> findById(String id) {
        String sql = "SELECT * FROM Vendors WHERE vendor_id = ?";
        List<Vendor> list = jdbcTemplate.query(sql, this::mapRow, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<Vendor> findByContact(String contact) {
        String sql = "SELECT * FROM Vendors WHERE contact = ?";
        List<Vendor> list = jdbcTemplate.query(sql, this::mapRow, contact);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Vendor> findAll() {
        return jdbcTemplate.query("SELECT * FROM Vendors", this::mapRow);
    }

    public int update(Vendor vendor) {
        String sql = "UPDATE Vendors SET vendor_name=?, brand_name=?, email=? WHERE vendor_id=?";
        return jdbcTemplate.update(sql,
                vendor.getVendorName(),
                vendor.getBrandName(),
                vendor.getEmail(),
                vendor.getVendorId());
    }

    public int delete(String id) {
        return jdbcTemplate.update("DELETE FROM Vendors WHERE vendor_id=?", id);
    }
}
