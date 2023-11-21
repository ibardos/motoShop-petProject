package com.ibardos.motoShop.service;

import com.ibardos.motoShop.service.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
