package com.example.pharmacyback.service;

import com.example.pharmacyback.model.Manufacturer;
import com.example.pharmacyback.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, Integer>> countManufacturersProducts() {
        try {
            System.out.println(manufacturerRepository.countManufacturersProducts());
            return manufacturerRepository.countManufacturersProducts();
        } catch (Exception e) {
            System.out.println();
            return null;
        }
    }
}
