package com.ibardos.motoShop.service.repository;

import com.ibardos.motoShop.entity.MotorcycleStock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing entities of MotorcycleStock type in the database.
 */
public interface MotorcycleStockRepository extends JpaRepository<MotorcycleStock, Integer> {
    List<MotorcycleStock> findAllByOrderByIdAsc();
}
