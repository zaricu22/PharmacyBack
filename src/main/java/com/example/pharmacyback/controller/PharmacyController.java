package com.example.pharmacyback.controller;

import com.example.pharmacyback.model.Manufacturer;
import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.service.ManufacturerService;
import com.example.pharmacyback.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
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
    public List<Product> getAllProducts(HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.getAllProducts();
        if(allProducts != null) {
            return allProducts;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @GetMapping(value = "/products/{id}")
    public Product getProductById(HttpServletResponse response, @PathVariable("id") UUID uuid) throws IOException {
        Product product = productService.getProductById(uuid);
        if(product != null) {
            return product;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @GetMapping(value = "/products/price/top-five")
    public List<Product> getTopFiveProductByPrice(HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.getTopFiveProductByPrice();
        if(allProducts != null) {
            return allProducts;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @GetMapping(value = "/products/price/least-five")
    public List<Product> getLeastFiveProductByPrice(HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.getLeastFiveProductByPrice();
        if(allProducts != null) {
            return allProducts;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @GetMapping(value = "/manufacturers")
    public List<Manufacturer> getAllManufacturers(HttpServletResponse response) throws IOException {
        List<Manufacturer> allManufacturers = manufacturerService.getAllManufacturers();
        if(allManufacturers != null) {
            return allManufacturers;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @GetMapping(value = "/manufacturers/products")
    public List<Map<String, Integer>> getManufacturersWithProducts(HttpServletResponse response) throws IOException {
        List<Map<String, Integer>> manusWithProducts = manufacturerService.countManufacturersProducts();
        if(manusWithProducts != null) {
            return manusWithProducts;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }

    @PostMapping(value = "/products")
    public void saveProduct(HttpServletResponse response, @RequestBody Product product) throws IOException {
        Product prod = productService.saveProduct(product);
    }

    @PutMapping(value = "/products/{id}")
    public void updateProduct(HttpServletResponse response, @PathVariable("id") UUID uuid, @RequestBody Product product) throws IOException {
        product.setId(uuid);
        Product prod = productService.saveProduct(product);
    }

    @DeleteMapping(value = "/products/{id}")
    public void deleteProduct(HttpServletResponse response, @PathVariable("id") UUID id) throws IOException {
        productService.deleteProduct(id);
    }
}
