package com.example.pharmacyback.crud;

import com.example.pharmacyback.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Integer> {
    List<Manufacturer> findAll();
}
