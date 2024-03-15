package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsCountDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsDTO;

import java.util.List;

public interface ManufacturerAppService {
    public List<ManufacturerDTO> getAllManufacturers();

    public List<ManufacturerProductsCountDTO> countManufacturersProducts();

    public List<ManufacturerProductsDTO> getManufacturersWithProducts();
}
