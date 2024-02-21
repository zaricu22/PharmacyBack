package com.example.pharmacyback.service;

import com.example.pharmacyback.repository.ManufacturerRepository;
import com.example.pharmacyback.model.Manufacturer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> getAllManufacturers() {
        try {
            return manufacturerRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
