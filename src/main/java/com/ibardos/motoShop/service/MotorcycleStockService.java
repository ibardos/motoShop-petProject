package com.ibardos.motoShop.service;

import com.ibardos.motoShop.entity.MotorcycleStock;
import com.ibardos.motoShop.repository.MotorcycleStockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Service class of MotorcycleStock type.
 */
@Service
public class MotorcycleStockService {
    MotorcycleStockRepository motorcycleStockRepository;

    @Autowired
    public MotorcycleStockService(MotorcycleStockRepository motorcycleStockRepository) {
        this.motorcycleStockRepository = motorcycleStockRepository;
    }


    /**
     * Adds the defined MotorcycleStock to DB.
     * @param motorcycleStock object to add.
     * @return the created MotorcycleStock object.
     */
    public MotorcycleStock add(MotorcycleStock motorcycleStock) {
        setCalculatedFieldsOfMotorcycleStockObjectFromClient(motorcycleStock);

        return motorcycleStockRepository.save(motorcycleStock);
    }

    /**
     * Gets a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to get.
     * @return MotorcycleStock object.
     */
    public MotorcycleStock get(int id) {
        Optional<MotorcycleStock> motorcycleStockFromDb = motorcycleStockRepository.findById(id);

        return motorcycleStockFromDb.orElse(null);
    }

    /**
     * Gets all existing MotorcycleStock from DB.
     * @return List of MotorcycleStock objects.
     */
    public List<MotorcycleStock> getAll() {
        return motorcycleStockRepository.findAllByOrderByIdAsc();
    }

    /**
     * Updates a MotorcycleStock in DB.
     * @param motorcycleStock object with updated properties.
     */
    public void update(MotorcycleStock motorcycleStock) {
        setCalculatedFieldsOfMotorcycleStockObjectFromClient(motorcycleStock);

        motorcycleStockRepository.save(motorcycleStock);
    }

    /**
     * Deletes a MotorcycleStock from DB by id.
     * @param id of the MotorcycleStock to delete.
     */
    public void delete(int id) {
        motorcycleStockRepository.deleteById(id);
    }


    // Private helper methods
    /**
     * Sets the values of those fields, that are need to be calculated, if MotorcycleStock model is coming from client.
     * @param motorcycleStock object from client, front-end.
     */
    private void setCalculatedFieldsOfMotorcycleStockObjectFromClient(MotorcycleStock motorcycleStock) {
        BigDecimal purchasingPrice = motorcycleStock.getPurchasingPrice();
        BigDecimal profitMargin = motorcycleStock.getProfitMargin();
        BigDecimal profitOnUnit = BigDecimal.valueOf(Math.ceil(Double.parseDouble(String.valueOf(purchasingPrice.multiply(profitMargin)))/100)*100);
        BigDecimal sellingPrice = purchasingPrice.add(profitOnUnit);

        motorcycleStock.setProfitOnUnit(profitOnUnit);
        motorcycleStock.setSellingPrice(sellingPrice);
    }
}
