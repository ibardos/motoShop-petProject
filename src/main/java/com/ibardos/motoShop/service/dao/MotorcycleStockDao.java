package com.ibardos.motoShop.service.dao;

import com.ibardos.motoShop.model.MotorcycleStock;

import java.util.List;

/**
 * Data Access Object interface of MotorcycleStock type.
 */
public interface MotorcycleStockDao {
    /**
     * Adds the defined MotorcycleStock to DB.
     * @param motorcycleStock object to add.
     * @return the created MotorcycleStock object.
     */
    MotorcycleStock add(MotorcycleStock motorcycleStock);

    /**
     * Gets a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to get.
     * @return MotorcycleStock object.
     */
    MotorcycleStock get(int id);

    /**
     * Gets all existing MotorcycleStock from DB.
     * @return List of MotorcycleStock objects.
     */
    List<MotorcycleStock> getAll();

    /**
     * Updates a MotorcycleStock in DB.
     * @param motorcycleStock object with updated properties.
     */
    void update(MotorcycleStock motorcycleStock);

    /**
     * Deletes a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to delete.
     */
    void delete(int id);
}
