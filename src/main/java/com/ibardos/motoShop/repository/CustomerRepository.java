package com.ibardos.motoShop.repository;

import com.ibardos.motoShop.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing entities of a Customer type in the database.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByOrderByIdAsc();
}
