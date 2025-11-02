package com.example.greenkart.greenkart_backend.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService service;

    // ----- OTP -----
    @PostMapping("/request_otp")
    public ResponseEntity<String> requestOtp(@RequestParam String contact) {
        return ResponseEntity.ok(service.requestOtp(contact));
    }

    @PostMapping("/verify_otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String contact, @RequestParam String vendor_otp) {
        return ResponseEntity.ok(service.verifyOtp(contact, vendor_otp));
    }

    // ----- CRUD -----
    @GetMapping("/all_vendors")
    public List<Vendor> getAllVendors() {
        return service.getAll();
    }

    @GetMapping("/get_vendor/{vendor_id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable("vendor_id") String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/update_vendor/{vendor_id}")
    public ResponseEntity<String> updateVendor(@PathVariable("vendor_id") String id, @RequestBody Vendor updated) {
        service.update(id, updated);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    @DeleteMapping("/delete_vendor/{vendor_id}")
    public ResponseEntity<String> deleteVendor(@PathVariable("vendor_id") String id) {
        service.delete(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
