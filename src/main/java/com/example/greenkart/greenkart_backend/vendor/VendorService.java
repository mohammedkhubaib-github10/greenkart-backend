package com.example.greenkart.greenkart_backend.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    
    @Autowired
    private VendorRepository vendorRepository;
    
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }
    
    public Vendor getVendorByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }
    
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
    
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}