package com.example.pharmacyback.service;

import com.example.pharmacyback.dto.CountManuProdDTO;
import com.example.pharmacyback.dto.ManuWithProdDTO;
import com.example.pharmacyback.model.Manufacturer;
import com.example.pharmacyback.repository.ManufacturerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService {

  private final ManufacturerRepository manufacturerRepository;

  public ManufacturerService(ManufacturerRepository manufacturerRepository) {
    this.manufacturerRepository = manufacturerRepository;
  }

  public List<Manufacturer> getAllManufacturers() {
    return manufacturerRepository.findAll();
  }

  public List<CountManuProdDTO> countManufacturersProducts() {
    return manufacturerRepository.countManufacturersProducts();
  }

  public List<ManuWithProdDTO> getManufacturersWithProducts() {
    return manufacturerRepository.getManufacturersWithProducts();
  }
}
