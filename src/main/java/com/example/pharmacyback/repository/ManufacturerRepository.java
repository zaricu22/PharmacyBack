package com.example.pharmacyback.repository;

import com.example.pharmacyback.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, UUID> {
    List<Manufacturer> findAll();
}
