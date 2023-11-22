package com.ibardos.motoShop.service.repository;

import com.ibardos.motoShop.model.Manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository interface for managing entities of Manufacturer type in the database.
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    List<Manufacturer> findAllByOrderByIdAsc();
}