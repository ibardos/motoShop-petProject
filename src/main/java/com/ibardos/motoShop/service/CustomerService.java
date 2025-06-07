package com.ibardos.motoShop.service;

import com.ibardos.motoShop.dto.CustomerDto;
import com.ibardos.motoShop.dto.CustomerUpdateDto;
import com.ibardos.motoShop.entity.Customer;
import com.ibardos.motoShop.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import jakarta.persistence.Transient;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

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

        // Ensure lazy-loaded orders are initialized to prevent LazyInitializationException
        Hibernate.initialize(savedCustomer.getOrders());

        return new CustomerDto(savedCustomer);
    }

    /**
     * Retrieves a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return CustomerDto containing the customer's data
     * @throws EntityNotFoundException if no customer exists with the specified ID
     */
    @Transactional
    public CustomerDto get(int id) {
        Customer customerFromDb = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));

        // Ensure lazy-loaded orders are initialized to prevent LazyInitializationException
        Hibernate.initialize(customerFromDb.getOrders());

        return new CustomerDto(customerFromDb);
    }

    /**
     * Retrieves a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return CustomerUpdateDto containing the customer's data
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
    @Transactional
    public List<CustomerDto> getAll() {
        List<Customer> customersFromDb = customerRepository.findAllByOrderByIdAsc();

        // Ensure lazy-loaded orders are initialized to prevent LazyInitializationException
        customersFromDb.forEach(customer -> Hibernate.initialize(customer.getOrders()));

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
        Customer customerFromDb = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + customer.getId() + " not found."));

        // Make sure the date of registration cannot be modified during an update
        customer.setDateOfRegistration( customerFromDb.getDateOfRegistration());

        customerRepository.save(customer);
    }

    /**
     * Deletes a customer record from the database by its ID.
     *
     * @param id The unique identifier of the customer to delete
     * @throws EntityNotFoundException if no customer exists with the specified ID
     * @throws IllegalStateException if the customer has any related order in the database
     */
    @Transactional
    public void delete(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));

        // Ensure lazy-loaded orders are initialized before checking isEmpty()
        Hibernate.initialize(customer.getOrders());

        if (!customer.getOrders().isEmpty()) {
            throw new IllegalStateException("Customer with id " + id + " cannot be deleted because it has orders.");
        }
        customerRepository.deleteById(id);
    }
}
