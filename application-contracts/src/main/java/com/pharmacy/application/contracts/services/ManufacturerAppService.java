package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.CountManuProdsDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.domain.model.Manufacturer;

import java.util.List;

public interface ManufacturerAppService {
    public List<Manufacturer> getAllManufacturers();

    public List<CountManuProdsDTO> countManufacturersProducts();

    public List<ManufacturerDTO> getManufacturersWithProducts();
}
