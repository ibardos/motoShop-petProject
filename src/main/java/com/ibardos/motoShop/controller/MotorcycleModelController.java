package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.model.MotorcycleModel;
import com.ibardos.motoShop.service.MotorcycleModelService;
import com.ibardos.motoShop.util.MotorcycleModelType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for MotorcycleModel type.
 */
@RestController
@RequestMapping("service/motorcycle/model")
public class MotorcycleModelController {
    MotorcycleModelService motorcycleModelService;

    public MotorcycleModelController(MotorcycleModelService motorcycleModelService) { this.motorcycleModelService = motorcycleModelService; }


    /**
     * Adds the defined MotorcycleModel to DB.
     * @param motorcycleModel object from the request's body.
     * @return the created MotorcycleModel object.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public MotorcycleModel add(@RequestBody MotorcycleModel motorcycleModel) {
        try {
            return motorcycleModelService.add(motorcycleModel);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to get, passed by path variable.
     * @return MotorcycleModel object.
     */
    @GetMapping("get/{id}")
    public MotorcycleModel get(@PathVariable int id) {
        MotorcycleModel motorcycleModel = motorcycleModelService.get(id);

        if (motorcycleModel == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return motorcycleModel;
    }

    /**
     * Gets all existing MotorcycleModel from DB.
     * @return List of MotorcycleModel objects.
     */
    @GetMapping("get/all")
    public List<MotorcycleModel> getAll() {
        List<MotorcycleModel> motorcycleModels = motorcycleModelService.getAll();

        if (motorcycleModels == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        return motorcycleModels;
    }

    /**
     * Updates a MotorcycleModel in DB.
     * @param motorcycleModel object with updated properties from the request's body.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("update")
    public void update(@RequestBody MotorcycleModel motorcycleModel) {
        MotorcycleModel motorcycleModelFromDb = motorcycleModelService.get(motorcycleModel.getId());
        if (motorcycleModelFromDb == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        try {
            motorcycleModelService.update(motorcycleModel);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a MotorcycleModel from DB by id.
     * @param id of the MotorcycleModel to delete, passed by path variable.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id) {
        MotorcycleModel motorcycleModel = motorcycleModelService.get(id);

        if (motorcycleModel == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        motorcycleModelService.delete(id);
    }

    /**
     * Gets all valid MotorcycleModel types.
     * @return List of MotorcycleModel types as Strings.
     */
    @GetMapping("get/types")
    public List<String> getMotorcycleModelTypes() {
        List<String> modelTypes = new ArrayList<>();

        for (MotorcycleModelType modelType : MotorcycleModelType.values()) {
            modelTypes.add(String.valueOf(modelType));
        }

        return modelTypes;
    }
}
