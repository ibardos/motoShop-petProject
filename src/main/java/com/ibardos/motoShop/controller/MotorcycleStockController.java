package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.model.MotorcycleStock;
import com.ibardos.motoShop.service.dao.MotorcycleStockDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST API controller for MotorcycleStock type.
 */
@RestController
@RequestMapping("motorcycle/stock")
public class MotorcycleStockController {
    MotorcycleStockDao motorcycleStockDao;

    public MotorcycleStockController(MotorcycleStockDao motorcycleStockDao) { this.motorcycleStockDao = motorcycleStockDao; }


    /**
     * Adds the defined MotorcycleStock to DB.
     * @param motorcycleStock object from the request's body.
     * @return the created MotorcycleStock object.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public MotorcycleStock add(@RequestBody MotorcycleStock motorcycleStock) {
        try {
            return motorcycleStockDao.add(motorcycleStock);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to get, passed by path variable.
     * @return MotorcycleStock object.
     */
    @GetMapping("get/{id}")
    public MotorcycleStock get(@PathVariable int id) {
        MotorcycleStock motorcycleStock = motorcycleStockDao.get(id);

        if (motorcycleStock == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return motorcycleStock;
    }

    /**
     * Gets all existing MotorcycleStock from DB.
     * @return List of MotorcycleStock objects.
     */
    @GetMapping("get/all")
    public List<MotorcycleStock> getAll() {
        List<MotorcycleStock> motorcycleStocks = motorcycleStockDao.getAll();

        if (motorcycleStocks == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return motorcycleStocks;
    }

    /**
     * Updates a MotorcycleStock in DB.
     * @param motorcycleStock object with updated properties from the request's body.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("update")
    public void update(@RequestBody MotorcycleStock motorcycleStock) {
        MotorcycleStock motorcycleStockFromDb = motorcycleStockDao.get(motorcycleStock.getId());
        if (motorcycleStockFromDb == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        try {
            motorcycleStockDao.update(motorcycleStock);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to delete, passed by path variable.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        MotorcycleStock motorcycleStock = motorcycleStockDao.get(id);

        if (motorcycleStock == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        motorcycleStockDao.delete(id);
    }
}
