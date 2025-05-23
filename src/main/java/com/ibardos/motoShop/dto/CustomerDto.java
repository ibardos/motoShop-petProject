package com.ibardos.motoShop.dto;

import com.ibardos.motoShop.entity.Customer;
import com.ibardos.motoShop.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO class for Customer entity.
 * <p>
 * This class holds only the fields that are needed for the frontend.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String fullAddress;
    private LocalDate dateOfRegistration;
    /**
     * List of order IDs associated with the customer.
     * Used to avoid circular references while maintaining order tracking capability.
     */
    private List<Integer> orderIds;


    /**
     * Constructor that maps Customer entity to CustomerDto.
     */
    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.fullName = firstName + " " + lastName;
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.fullAddress = customer.getAddress() + ", " +
                customer.getCity() + " " +
                customer.getPostalCode() + ", " +
                customer.getCountry();
        this.dateOfRegistration = customer.getDateOfRegistration();
        this.orderIds = customer.getOrders()
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }
}