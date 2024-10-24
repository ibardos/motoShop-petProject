package com.ibardos.motoShop.dao;

import com.ibardos.motoShop.dao.generic.GenericDao;
import com.ibardos.motoShop.model.Manufacturer;

import java.util.List;

/**
 * Data Access Object interface of Manufacturer type.
 */
public interface ManufacturerDao extends GenericDao<Manufacturer, Integer> {
    /**
     * Adds the defined Manufacturer to DB.
     * @param manufacturer object to add.
     * @return the created Manufacturer object.
     */
    Manufacturer add(Manufacturer manufacturer);

    /**
     * Gets a Manufacturer from DB by id.
     * @param id of the Manufacturer to get.
     * @return Manufacturer object.
     */
    Manufacturer get(Integer id);

    /**
     * Gets all existing Manufacturer from DB.
     * @return List of Manufacturer objects.
     */
    List<Manufacturer> getAll();

    /**
     * Updates a Manufacturer in DB.
     * @param manufacturer object with updated properties.
     */
    void update(Manufacturer manufacturer);

    /**
     * Deletes a Manufacturer from DB by id.
     * @param id of the Manufacturer to delete.
     */
    void delete(Integer id);
}
