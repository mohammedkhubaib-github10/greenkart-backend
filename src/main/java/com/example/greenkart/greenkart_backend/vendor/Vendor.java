package com.example.greenkart.greenkart_backend.vendor;

import com.example.greenkart.greenkart_backend.address.Address;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String description;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}