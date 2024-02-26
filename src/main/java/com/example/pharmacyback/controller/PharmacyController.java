package com.example.pharmacyback.controller;

import com.example.pharmacyback.model.Manufacturer;
import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.service.ManufacturerService;
import com.example.pharmacyback.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class PharmacyController {

    private final ProductService productService;

    private final ManufacturerService manufacturerService;

    public PharmacyController(ProductService productService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID uuid) {
        Product productById = productService.getProductById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(productById);
    }

    @GetMapping(value = "/products/price/top-five")
    public ResponseEntity<?> getTopFiveProductByPrice() {
        List<Product> topFiveProducts = productService.getTopFiveProductByPrice();
        return ResponseEntity.status(HttpStatus.OK).body(topFiveProducts);
    }

    @GetMapping(value = "/products/price/least-five")
    public ResponseEntity<?> getLeastFiveProductByPrice() {
        List<Product> leastFiveProducts = productService.getLeastFiveProductByPrice();
        return ResponseEntity.status(HttpStatus.OK).body(leastFiveProducts);
    }

    @GetMapping(value = "/manufacturers")
    public ResponseEntity<?> getAllManufacturers() {
        List<Manufacturer> allManufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.status(HttpStatus.OK).body(allManufacturers);
    }

    @GetMapping(value = "/manufacturers/products")
    public ResponseEntity<?> getManufacturersWithProducts() {
        List<Map<String, Integer>> manusWithProducts = manufacturerService.countManufacturersProducts();
        return ResponseEntity.status(HttpStatus.OK).body(manusWithProducts);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        Product prod = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") UUID uuid, @RequestBody Product product) {
        product.setId(uuid);
        Product prod = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
