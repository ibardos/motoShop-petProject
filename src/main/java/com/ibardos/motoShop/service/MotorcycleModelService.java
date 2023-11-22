package com.ibardos.motoShop.service;

import com.ibardos.motoShop.model.MotorcycleModel;
import com.ibardos.motoShop.service.repository.MotorcycleModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Service class of MotorcycleModel type.
 */
@Service
public class MotorcycleModelService {
    MotorcycleModelRepository motorcycleModelRepository;

    @Autowired
    public MotorcycleModelService(MotorcycleModelRepository motorcycleModelRepository) {
        this.motorcycleModelRepository = motorcycleModelRepository;
    }

    public MotorcycleModel add(MotorcycleModel motorcycleModel) { return motorcycleModelRepository.save(motorcycleModel); }

    public MotorcycleModel get(int id) {
        Optional<MotorcycleModel> motorcycleModelFromDb = motorcycleModelRepository.findById(id);

        return motorcycleModelFromDb.orElse(null);
    }

    public List<MotorcycleModel> getAll() {
        return motorcycleModelRepository.findAllByOrderByIdAsc();
    }

    public void update(MotorcycleModel manufacturer) {
        motorcycleModelRepository.save(manufacturer);
    }

    public void delete(int id) {
        motorcycleModelRepository.deleteById(id);
    }
}
