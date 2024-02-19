package com.example.pharmacyback.services;

import com.example.pharmacyback.repository.ManufacturerRepository;
import com.example.pharmacyback.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    List<Manufacturer> getAllProducts() {
        try {
            return manufacturerRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
