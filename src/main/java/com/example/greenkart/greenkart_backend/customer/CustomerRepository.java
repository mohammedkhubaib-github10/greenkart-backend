package com.example.greenkart.greenkart_backend.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getString("customer_id"),
                rs.getString("customer_name"),
                rs.getString("email"),
                rs.getString("contact"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    public void save(Customer customer) {
        String sql = "INSERT INTO Customers (customer_id, customer_name, email, contact, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getContact(),
                customer.getCreatedAt());
    }

    public Optional<Customer> findById(String id) {
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        List<Customer> list = jdbcTemplate.query(sql, this::mapRow, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<Customer> findByContact(String contact) {
        String sql = "SELECT * FROM Customers WHERE contact = ?";
        List<Customer> list = jdbcTemplate.query(sql, this::mapRow, contact);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM Customers", this::mapRow);
    }

    public int update(Customer customer) {
        String sql = "UPDATE Customers SET customer_name=?, email=? WHERE customer_id=?";
        return jdbcTemplate.update(sql,
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getCustomerId());
    }

    public int delete(String id) {
        return jdbcTemplate.update("DELETE FROM Customers WHERE customer_id=?", id);
    }
}
