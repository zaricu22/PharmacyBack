package com.pharmacy.application.services;

import com.pharmacy.application.contracts.services.ManufacturerAppService;
import com.pharmacy.application.contracts.dtos.CountManuProdsDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.infrastructure.repositories.ManufacturerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerAppServiceImpl implements ManufacturerAppService {

    @Autowired
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerAppServiceImpl(ManufacturerRepositoryImpl manufacturerRepositoryImpl) {
        this.manufacturerRepository = manufacturerRepositoryImpl;
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public List<CountManuProdsDTO> countManufacturersProducts() {
        return manufacturerRepository.countManufacturersProducts();
    }

    public List<ManufacturerDTO> getManufacturersWithProducts() {
       return manufacturerRepository.getManufacturersWithProducts();
    }
}
