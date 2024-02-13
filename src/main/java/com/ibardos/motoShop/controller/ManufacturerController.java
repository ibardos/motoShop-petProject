package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.model.Manufacturer;
import com.ibardos.motoShop.service.ManufacturerService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST API controller for Manufacturer type.
 */
@RestController
@RequestMapping("service/manufacturer")
public class ManufacturerController {
    ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) { this.manufacturerService = manufacturerService; }


    /**
     * Adds the defined Manufacturer to DB.
     * @param manufacturer object from the request's body.
     * @return the created Manufacturer object.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public Manufacturer add(@RequestBody Manufacturer manufacturer) {
        try {
            return manufacturerService.add(manufacturer);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a Manufacturer from DB by id.
     * @param id of the Manufacturer to get, passed by path variable.
     * @return Manufacturer object.
     */
    @GetMapping("get/{id}")
    public Manufacturer get(@PathVariable int id) {
        Manufacturer manufacturer = manufacturerService.get(id);

        if (manufacturer == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return manufacturer;
    }

    /**
     * Gets all existing Manufacturer from DB.
     * @return List of Manufacturer objects.
     */
    @GetMapping("get/all")
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = manufacturerService.getAll();

        if (manufacturers == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return manufacturers;
    }

    /**
     * Updates a Manufacturer in DB.
     * @param manufacturer object with updated properties from the request's body.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("update")
    public void update(@RequestBody Manufacturer manufacturer) {
        Manufacturer manufacturerFromDb = manufacturerService.get(manufacturer.getId());
        if (manufacturerFromDb == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        try {
            manufacturerService.update(manufacturer);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a Manufacturer from DB by id.
     * @param id of the Manufacturer to delete, passed by path variable.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id) {
        Manufacturer manufacturerFromDb = manufacturerService.get(id);

        if (manufacturerFromDb == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        manufacturerService.delete(id);
    }
}
