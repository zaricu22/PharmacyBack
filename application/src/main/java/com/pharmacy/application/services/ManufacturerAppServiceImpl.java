package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsCountDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsDTO;
import com.pharmacy.application.contracts.mappers.ManufacturerMapper;
import com.pharmacy.application.contracts.services.ManufacturerAppService;
import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import com.pharmacy.infrastructure.mappers.ManufacturerMapperImpl;
import com.pharmacy.infrastructure.repositories.ManufacturerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerAppServiceImpl implements ManufacturerAppService {

    @Autowired
    private final ManufacturerRepository manufacturerRepository;

    private final ManufacturerMapper manufacturerMapper;

    public ManufacturerAppServiceImpl(
            ManufacturerRepositoryImpl manufacturerRepositoryImpl,
            ManufacturerMapperImpl manufacturerMapperImpl)
    {
        this.manufacturerRepository = manufacturerRepositoryImpl;
        this.manufacturerMapper = manufacturerMapperImpl;
    }

    public List<ManufacturerDTO> getAllManufacturers() {
        List<Manufacturer> manufactuerList = manufacturerRepository.findAll();
        return manufacturerMapper.toDto(manufactuerList);
    }

    public List<ManufacturerProductsCountDTO> countManufacturersProducts() {
        List<Manufacturer> manufactuerList = manufacturerRepository.findAll();
        return manufacturerMapper.mapToProductsCountList(manufactuerList);
    }

    public List<ManufacturerProductsDTO> getManufacturersWithProducts() {
        List<Manufacturer> manufactuerList = manufacturerRepository.findAll();
        return manufacturerMapper.mapToManufacturerProductsList(manufactuerList);
    }

}
