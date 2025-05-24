package com.ibardos.motoShop.service;

import com.ibardos.motoShop.dto.CustomerDto;
import com.ibardos.motoShop.dto.CustomerUpdateDto;
import com.ibardos.motoShop.entity.Customer;
import com.ibardos.motoShop.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles business logic for Customer entities.
 * <p>
 * Provides methods for CRUD operations and data transformation between Customer entities and DTOs.
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Creates a new customer record in the database with the details provided in the DTO.
     *
     * @param customer The Customer entity to be persisted
     * @return CustomerDto containing the data of the newly created customer
     */
    public CustomerDto add(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerDto(savedCustomer);
    }

    /**
     * Retrieves a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return CustomerDto containing the customer's data
     * @throws EntityNotFoundException if no customer exists with the specified ID
     */
    public CustomerDto get(int id) {
        Customer customerFromDb = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));

        return new CustomerDto(customerFromDb);
    }

    /**
     * Retrieves a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return CustomerDto containing the customer's data
     * @throws EntityNotFoundException if no customer exists with the specified ID
     */
    public CustomerUpdateDto getUpdateDto(int id) {
        Customer customerFromDb = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));

        return new CustomerUpdateDto(customerFromDb);
    }

    /**
     * Retrieves all customer records from the database.
     *
     * @return List of CustomerDto objects containing all customers' data, sorted by ID in ascending order
     * @throws EntityNotFoundException if no customers exist in the database
     */
    public List<CustomerDto> getAll() {
        List<Customer> customersFromDb = customerRepository.findAllByOrderByIdAsc();

        if (customersFromDb.isEmpty()) { throw new EntityNotFoundException("No customers found."); }

        return customersFromDb.stream()
                .map(CustomerDto::new)
                .toList();
    }

    /**
     * Updates an existing customer record in the database.
     *
     * @param customer The Customer entity containing updated data
     * @throws EntityNotFoundException if no customer exists with the ID specified in the customer parameter
     */
    public void update(Customer customer) {
        if (customerRepository.findById(customer.getId()).isEmpty()) {
            throw new EntityNotFoundException("Customer with id: " + customer.getId() + " not found.");
        }

        customerRepository.save(customer);
    }

    /**
     * Deletes a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to delete
     * @throws EntityNotFoundException if no customer exists with the specified ID
     * @throws IllegalStateException if the customer has any related order in the database
     */
    public void delete(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));

        if (!customer.getOrders().isEmpty()) {
            throw new IllegalStateException("Customer with id " + id + " cannot be deleted because it has orders.");
        }

        customerRepository.deleteById(id);
    }
}