package com.example.greenkart.greenkart_backend.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class AddressRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ---- Customer Address ----
    private CustomerAddress mapCustomer(ResultSet rs, int rowNum) throws SQLException {
        CustomerAddress addr = new CustomerAddress();
        addr.setAddressId(rs.getString("address_id"));
        addr.setStreet(rs.getString("street"));
        addr.setPincode(rs.getString("pincode"));
        addr.setCity(rs.getString("city"));
        addr.setFlatNo(rs.getString("flat_no"));
        addr.setLatitude(rs.getString("latitude"));
        addr.setLongitude(rs.getString("longitude"));
        addr.setCustomerId(rs.getString("customer_id"));
        return addr;
    }

    public void addCustomerAddress(CustomerAddress address) {
        String sql = "INSERT INTO Customer_Addresses (address_id, street, pincode, city, flat_no, latitude, longitude, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                UUID.randomUUID().toString(),
                address.getStreet(),
                address.getPincode(),
                address.getCity(),
                address.getFlatNo(),
                address.getLatitude(),
                address.getLongitude(),
                address.getCustomerId());
    }

    public List<CustomerAddress> getCustomerAddresses(String customerId) {
        String sql = "SELECT * FROM Customer_Addresses WHERE customer_id = ?";
        return jdbcTemplate.query(sql, this::mapCustomer, customerId);
    }

    public int updateCustomerAddress(CustomerAddress address) {
        String sql = "UPDATE Customer_Addresses SET street=?, pincode=?, city=?, flat_no=?, latitude=?, longitude=? WHERE address_id=? AND customer_id=?";
        return jdbcTemplate.update(sql,
                address.getStreet(),
                address.getPincode(),
                address.getCity(),
                address.getFlatNo(),
                address.getLatitude(),
                address.getLongitude(),
                address.getAddressId(),
                address.getCustomerId());
    }

    public int deleteCustomerAddress(String addressId, String customerId) {
        String sql = "DELETE FROM Customer_Addresses WHERE address_id=? AND customer_id=?";
        return jdbcTemplate.update(sql, addressId, customerId);
    }

    // ---- Vendor Address ----
    private VendorAddress mapVendor(ResultSet rs, int rowNum) throws SQLException {
        VendorAddress addr = new VendorAddress();
        addr.setAddressId(rs.getString("address_id"));
        addr.setStreet(rs.getString("street"));
        addr.setPincode(rs.getString("pincode"));
        addr.setCity(rs.getString("city"));
        addr.setLatitude(rs.getString("latitude"));
        addr.setLongitude(rs.getString("longitude"));
        addr.setVendorId(rs.getString("vendor_id"));
        return addr;
    }

    public void addVendorAddress(VendorAddress address) {
        String sql = "INSERT INTO Vendor_Addresses (address_id, street, pincode, city, latitude, longitude, vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                UUID.randomUUID().toString(),
                address.getStreet(),
                address.getPincode(),
                address.getCity(),
                address.getLatitude(),
                address.getLongitude(),
                address.getVendorId());
    }

    public List<VendorAddress> getVendorAddresses(String vendorId) {
        String sql = "SELECT * FROM Vendor_Addresses WHERE vendor_id = ?";
        return jdbcTemplate.query(sql, this::mapVendor, vendorId);
    }

    public int updateVendorAddress(VendorAddress address) {
        String sql = "UPDATE Vendor_Addresses SET street=?, pincode=?, city=?, latitude=?, longitude=? WHERE address_id=? AND vendor_id=?";
        return jdbcTemplate.update(sql,
                address.getStreet(),
                address.getPincode(),
                address.getCity(),
                address.getLatitude(),
                address.getLongitude(),
                address.getAddressId(),
                address.getVendorId());
    }

    public int deleteVendorAddress(String addressId, String vendorId) {
        String sql = "DELETE FROM Vendor_Addresses WHERE address_id=? AND vendor_id=?";
        return jdbcTemplate.update(sql, addressId, vendorId);
    }
}
