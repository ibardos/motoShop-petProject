package com.ibardos.motoShop.service.dao;

import com.ibardos.motoShop.model.MotorcycleModel;

import java.util.List;

/**
 * Data Access Object interface of MotorcycleModel type.
 */
public interface MotorcycleModelDao {
    /**
     * Adds the defined MotorcycleModel to DB.
     * @param motorcycleModel object to add.
     * @return the created MotorcycleModel object.
     */
    MotorcycleModel add(MotorcycleModel motorcycleModel);

    /**
     * Gets a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to get.
     * @return MotorcycleModel object.
     */
    MotorcycleModel get(int id);

    /**
     * Gets all existing MotorcycleModel from DB.
     * @return List of MotorcycleModel objects.
     */
    List<MotorcycleModel> getAll();

    /**
     * Updates a MotorcycleModel in DB.
     * @param motorcycleModel object with updated properties.
     */
    void update(MotorcycleModel motorcycleModel);

    /**
     * Deletes a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to delete.
     */
    void delete(int id);
}
