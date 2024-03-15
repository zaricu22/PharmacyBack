package com.pharmacy.domain.repositories;

import com.pharmacy.application.contracts.dtos.CountManuProdsDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.domain.model.Manufacturer;

import java.util.List;
import java.util.UUID;

public interface ManufacturerRepository {
    List<Manufacturer> findAll();

    boolean existsById(UUID id);

    List<CountManuProdsDTO> countManufacturersProducts();

    List<ManufacturerDTO> getManufacturersWithProducts();
}
