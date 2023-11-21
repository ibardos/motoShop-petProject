package com.ibardos.motoShop.service;

import com.ibardos.motoShop.model.Manufacturer;
import com.ibardos.motoShop.service.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Service class of Manufacturer type.
 */
@Service
public class ManufacturerService {
    ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer add(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public Manufacturer get(int id) {
        Optional<Manufacturer> manufacturerFromDb = manufacturerRepository.findById(id);

        return manufacturerFromDb.orElse(null);
    }

    public List<Manufacturer> getAll() {
        return manufacturerRepository.findAllByOrderByIdAsc();
    }

    public void update(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    public void delete(int id) {
        manufacturerRepository.deleteById(id);
    }
}
