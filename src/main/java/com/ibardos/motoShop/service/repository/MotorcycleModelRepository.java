package com.ibardos.motoShop.service.repository;

import com.ibardos.motoShop.model.MotorcycleModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing entities of MotorcycleModel type in the database.
 */
public interface MotorcycleModelRepository extends JpaRepository<MotorcycleModel, Integer> {
    List<MotorcycleModel> findAllByOrderByIdAsc();
}