package com.ibardos.motoShop.service;

import com.ibardos.motoShop.entity.Manufacturer;
import com.ibardos.motoShop.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Service class of Manufacturer type.
 */
@Service
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    /**
     * Adds the defined Manufacturer to DB.
     * @param manufacturer object to add.
     * @return the created Manufacturer object.
     */
    public Manufacturer add(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    /**
     * Gets a Manufacturer from DB by id.
     * @param id of the Manufacturer to get.
     * @return Manufacturer object, or null if not found.
     */
    public Manufacturer get(int id) {
        Optional<Manufacturer> manufacturerFromDb = manufacturerRepository.findById(id);

        return manufacturerFromDb.orElse(null);
    }

    /**
     * Gets all existing Manufacturer from DB.
     * @return List of Manufacturer objects.
     */
    public List<Manufacturer> getAll() {
        return manufacturerRepository.findAllByOrderByIdAsc();
    }

    /**
     * Updates a Manufacturer in DB.
     * @param manufacturer object with updated properties.
     */
    public void update(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    /**
     * Deletes a Manufacturer from DB by id.
     * @param id of the Manufacturer to delete.
     */
    public void delete(int id) {
        manufacturerRepository.deleteById(id);
    }
}
