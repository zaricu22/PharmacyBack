package com.pharmacy.presentation.controllers;

import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsCountDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsDTO;
import com.pharmacy.application.contracts.services.ManufacturerAppService;
import com.pharmacy.application.services.ManufacturerAppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ManufacturerController {

    @Autowired
    private final ManufacturerAppService manufacturerAppService;

    public ManufacturerController(ManufacturerAppServiceImpl manufacturerAppServiceImpl) {
        this.manufacturerAppService = manufacturerAppServiceImpl;
    }

    @GetMapping(value = "/manufacturers")
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers() {
        List<ManufacturerDTO> allManufacturers = manufacturerAppService.getAllManufacturers();
        return ResponseEntity.status(HttpStatus.OK).body(allManufacturers);
    }

    @GetMapping(value = "/manufacturers/products")
    public ResponseEntity<?> getManufacturersWithProductsList() {
        List<ManufacturerProductsDTO> manusWithProductsCount =
                manufacturerAppService.getManufacturersWithProducts();
        return ResponseEntity.status(HttpStatus.OK).body(manusWithProductsCount);
    }

    @GetMapping(
            value = "/manufacturers/products",
            params = {"countOnly"})
    public ResponseEntity<?> getManufacturersWithProductsCount(
            @RequestParam("countOnly") boolean countOnly) {
        List<ManufacturerProductsCountDTO> manusWithProductsCount =
                manufacturerAppService.countManufacturersProducts();
        return ResponseEntity.status(HttpStatus.OK).body(manusWithProductsCount);
    }
}
