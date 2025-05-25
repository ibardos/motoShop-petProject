package com.ibardos.motoShop.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Customer in the database.
 * <p>
 * This class holds personal information about the customer such as name, contact details,
 * and address. It also contains a list of orders associated with the customer.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private LocalDate dateOfRegistration;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    // Field level initialization to avoid problems with JPA created entities.
    private List<Order> orders = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.dateOfRegistration = LocalDate.now();
    }
}