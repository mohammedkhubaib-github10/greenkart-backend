package com.example.greenkart.greenkart_backend.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public void addCustomerAddress(CustomerAddress address) {
        repo.addCustomerAddress(address);
    }

    public List<CustomerAddress> getCustomerAddresses(String customerId) {
        return repo.getCustomerAddresses(customerId);
    }

    public void updateCustomerAddress(CustomerAddress address) {
        repo.updateCustomerAddress(address);
    }

    public void deleteCustomerAddress(String addressId, String customerId) {
        repo.deleteCustomerAddress(addressId, customerId);
    }

    public void addVendorAddress(VendorAddress address) {
        repo.addVendorAddress(address);
    }

    public List<VendorAddress> getVendorAddresses(String vendorId) {
        return repo.getVendorAddresses(vendorId);
    }

    public void updateVendorAddress(VendorAddress address) {
        repo.updateVendorAddress(address);
    }

    public void deleteVendorAddress(String addressId, String vendorId) {
        repo.deleteVendorAddress(addressId, vendorId);
    }
}
