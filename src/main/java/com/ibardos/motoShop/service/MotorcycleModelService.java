package com.ibardos.motoShop.service;

import com.ibardos.motoShop.entity.MotorcycleModel;
import com.ibardos.motoShop.repository.MotorcycleModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Service class of MotorcycleModel type.
 */
@Service
public class MotorcycleModelService {
    private final MotorcycleModelRepository motorcycleModelRepository;

    @Autowired
    public MotorcycleModelService(MotorcycleModelRepository motorcycleModelRepository) {
        this.motorcycleModelRepository = motorcycleModelRepository;
    }


    /**
     * Adds the defined MotorcycleModel to DB.
     * @param motorcycleModel object to add.
     * @return the created MotorcycleModel object.
     */
    public MotorcycleModel add(MotorcycleModel motorcycleModel) { return motorcycleModelRepository.save(motorcycleModel); }

    /**
     * Gets a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to get.
     * @return MotorcycleModel object, or null if not found.
     */
    public MotorcycleModel get(int id) {
        Optional<MotorcycleModel> motorcycleModelFromDb = motorcycleModelRepository.findById(id);

        return motorcycleModelFromDb.orElse(null);
    }

    /**
     * Gets all existing MotorcycleModel from DB.
     * @return List of MotorcycleModel objects.
     */
    public List<MotorcycleModel> getAll() {
        return motorcycleModelRepository.findAllByOrderByIdAsc();
    }

    /**
     * Updates a MotorcycleModel in DB.
     * @param motorcycleModel object with updated properties.
     */
    public void update(MotorcycleModel motorcycleModel) {
        motorcycleModelRepository.save(motorcycleModel);
    }

    /**
     * Deletes a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to delete.
     */
    public void delete(int id) {
        motorcycleModelRepository.deleteById(id);
    }
}
