package com.ibardos.motoShop.repository;

import com.ibardos.motoShop.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing Orders in the database.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByOrderByOrderDateDesc();
}
