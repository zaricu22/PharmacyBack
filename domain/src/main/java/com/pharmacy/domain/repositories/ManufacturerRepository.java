package com.pharmacy.domain.repositories;

import com.pharmacy.domain.model.Manufacturer;

import java.util.List;
import java.util.UUID;

public interface ManufacturerRepository {
    List<Manufacturer> findAll();

    boolean existsById(UUID id);
//
//    List<CountManuProdsDTO> countManufacturersProducts();
//
//    List<Manufacturer> getManufacturersWithProducts();
}
