package com.ibardos.motoShop.dto;

import com.ibardos.motoShop.entity.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for Customer entity.
 * <p>
 * This class holds only the fields that are needed for the frontend during and update request.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;


    /**
     * Constructor that maps Customer entity to CustomerUpdateDto.
     */
    public CustomerUpdateDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
        this.city = customer.getCity();
        this.postalCode = customer.getPostalCode();
        this.country = customer.getCountry();
    }
}