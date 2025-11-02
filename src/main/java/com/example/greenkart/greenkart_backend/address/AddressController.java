package com.example.greenkart.greenkart_backend.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService service;

    // ----- Customer -----
    @PostMapping("/new_customer_address/{customer_id}")
    public ResponseEntity<String> addCustomerAddress(@PathVariable("customer_id") String customerId, @RequestBody CustomerAddress address) {
        address.setCustomerId(customerId);
        service.addCustomerAddress(address);
        return ResponseEntity.ok("Customer address added successfully");
    }

    @GetMapping("/get_customer_addresses/{customer_id}")
    public List<CustomerAddress> getCustomerAddresses(@PathVariable("customer_id") String customerId) {
        return service.getCustomerAddresses(customerId);
    }

    @PutMapping("/update_customer_address/{customer_id}/{address_id}")
    public ResponseEntity<String> updateCustomerAddress(@PathVariable("customer_id") String customerId,
                                                        @PathVariable("address_id") String addressId,
                                                        @RequestBody CustomerAddress address) {
        address.setCustomerId(customerId);
        address.setAddressId(addressId);
        service.updateCustomerAddress(address);
        return ResponseEntity.ok("Customer address updated successfully");
    }

    @DeleteMapping("/delete_customer_address/{customer_id}/{address_id}")
    public ResponseEntity<String> deleteCustomerAddress(@PathVariable("customer_id") String customerId,
                                                        @PathVariable("address_id") String addressId) {
        service.deleteCustomerAddress(addressId, customerId);
        return ResponseEntity.ok("Customer address deleted successfully");
    }

    // ----- Vendor -----
    @PostMapping("/new_vendor_address/{vendor_id}")
    public ResponseEntity<String> addVendorAddress(@PathVariable("vendor_id") String vendorId, @RequestBody VendorAddress address) {
        address.setVendorId(vendorId);
        service.addVendorAddress(address);
        return ResponseEntity.ok("Vendor address added successfully");
    }

    @GetMapping("/get_vendor_addresses/{vendor_id}")
    public List<VendorAddress> getVendorAddresses(@PathVariable("vendor_id") String vendorId) {
        return service.getVendorAddresses(vendorId);
    }

    @PutMapping("/update_vendor_address/{vendor_id}/{address_id}")
    public ResponseEntity<String> updateVendorAddress(@PathVariable("vendor_id") String vendorId,
                                                      @PathVariable("address_id") String addressId,
                                                      @RequestBody VendorAddress address) {
        address.setVendorId(vendorId);
        address.setAddressId(addressId);
        service.updateVendorAddress(address);
        return ResponseEntity.ok("Vendor address updated successfully");
    }

    @DeleteMapping("/delete_vendor_address/{vendor_id}/{address_id}")
    public ResponseEntity<String> deleteVendorAddress(@PathVariable("vendor_id") String vendorId,
                                                      @PathVariable("address_id") String addressId) {
        service.deleteVendorAddress(addressId, vendorId);
        return ResponseEntity.ok("Vendor address deleted successfully");
    }
}
