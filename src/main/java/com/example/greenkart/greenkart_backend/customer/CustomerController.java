package com.example.greenkart.greenkart_backend.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    // ----- OTP -----
    @PostMapping("/request_otp")
    public ResponseEntity<String> requestOtp(@RequestParam String contact) {
        return ResponseEntity.ok(service.requestOtp(contact));
    }

    @PostMapping("/verify_otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String contact, @RequestParam String customer_otp) {
        return ResponseEntity.ok(service.verifyOtp(contact, customer_otp));
    }

    // ----- CRUD -----
    @GetMapping("/all_customers")
    public List<Customer> getAllCustomers() {
        return service.getAll();
    }

    @GetMapping("/get_customer/{customer_id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customer_id") String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/update_customer/{customer_id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("customer_id") String id, @RequestBody Customer updated) {
        service.update(id, updated);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @DeleteMapping("/delete_customer/{customer_id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customer_id") String id) {
        service.delete(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
