package com.ibardos.motoShop.dao.generic;

import java.util.List;

/**
 * Generic Data Access Object (DAO) interface that defines basic CRUD operations.
 * @param <T> the type of the entity for which the implementing DAO class will be responsible.
 * @param <ID> the type of the primary key of the entity for which the implementing DAO class will be responsible.
 */
public interface GenericDao<T, ID> {
    /**
     * Adds a new entity of type T to the database.
     * @param entity the entity to be added.
     * @return the entity of type T that has been added to the database.
     */
    T add(T entity);

    /**
     * Gets an entity from the database by its unique ID.
     * @param id the unique identifier of the entity to get.
     * @return the entity of type T from the database if found, otherwise null.
     */
    T get(ID id);

    /**
     * Gets all entities of type T from the database.
     * @return List of entities of type T from the database if there is any, otherwise null.
     */
    List<T> getAll();

    /**
     * Updates an existing entity of type T at the database.
     * @param entity the entity with updated properties.
     */
    void update(T entity);

    /**
     * Deletes an entity from the database by its unique id.
     * @param id the unique identifier of the entity to be deleted.
     */
    void delete(ID id);
}
