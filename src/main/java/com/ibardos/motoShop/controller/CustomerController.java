package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.dto.CustomerDto;
import com.ibardos.motoShop.dto.CustomerUpdateDto;
import com.ibardos.motoShop.entity.Customer;
import com.ibardos.motoShop.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST API controller handling Customer-related HTTP requests.
 * <p>
 * All endpoints return data in DTO format to ensure proper data encapsulation
 * and handle various exceptions with appropriate HTTP status codes.
 */
@RestController
@RequestMapping("/service/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a new customer in the database.
     *
     * @param customer The customer object to be created, provided in the request body
     * @return CustomerDto containing the created customer's information
     * @throws ResponseStatusException with BAD_REQUEST status if there are validation or constraint violations
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public CustomerDto add(@RequestBody Customer customer) {
        try {
            return customerService.add(customer);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a specific customer by its ID.
     *
     * @param id The ID of the customer to retrieve
     * @return CustomerDto containing the requested customer's information
     * @throws ResponseStatusException with NOT_FOUND status if the customer doesn't exist
     */
    @GetMapping("get/{id}")
    public CustomerDto get(@PathVariable int id) {
        try {
            return customerService.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific customer by its ID, and returns a mapped CustomerUpdateDto.
     *
     * @param id The ID of the customer to retrieve
     * @return CustomerUpdateDto containing the requested customer's information
     * @throws ResponseStatusException with NOT_FOUND status if the customer doesn't exist
     */
    @GetMapping("get/updateDto/{id}")
    public CustomerUpdateDto getUpdateDto(@PathVariable int id) {
        try {
            return customerService.getUpdateDto(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return List of CustomerDto objects containing all customers' information
     * @throws ResponseStatusException with NOT_FOUND status if no customers exist
     */
    @GetMapping("get/all")
    public List<CustomerDto> getAll() {
        try {
            return customerService.getAll();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer The customer object with updated information, provided in the request body
     * @throws ResponseStatusException with BAD_REQUEST status if there are validation or constraint violations
     * @throws ResponseStatusException with NOT_FOUND status if the customer to update doesn't exist
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("update")
    public void update(@RequestBody Customer customer) {
        try {
            customerService.update(customer);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Deletes a customer from the database by its ID.
     *
     * @param id The unique identifier of the customer to delete
     * @throws ResponseStatusException with BAD_REQUEST status if the customer cannot be deleted due to constraints
     * @throws ResponseStatusException with NOT_FOUND status if the customer to delete doesn't exist
     * @throws ResponseStatusException with CONFLICT status if the customer to delete has any related order in the database
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id) {
        try {
            customerService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
}