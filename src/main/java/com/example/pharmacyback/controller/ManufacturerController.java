package com.example.pharmacyback.controller;

import com.example.pharmacyback.dto.CountManuProdDTO;
import com.example.pharmacyback.dto.ManuWithProdDTO;
import com.example.pharmacyback.model.Manufacturer;
import com.example.pharmacyback.service.ManufacturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping(value = "/manufacturers")
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        List<Manufacturer> allManufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.status(HttpStatus.OK).body(allManufacturers);
    }

    @GetMapping(value = "/manufacturers/products")
    public ResponseEntity<?> getManufacturersWithProductsList() {
        List<ManuWithProdDTO> manusWithProductsCount = manufacturerService.getManufacturersWithProducts();
        return ResponseEntity.status(HttpStatus.OK).body(manusWithProductsCount);
    }

    @GetMapping(value = "/manufacturers/products", params = {"countOnly"})
    public ResponseEntity<?> getManufacturersWithProductsCount(
            @RequestParam("countOnly") boolean countOnly
    ) {
        List<CountManuProdDTO> manusWithProductsCount = manufacturerService.countManufacturersProducts();
        return ResponseEntity.status(HttpStatus.OK).body(manusWithProductsCount);
    }
}
