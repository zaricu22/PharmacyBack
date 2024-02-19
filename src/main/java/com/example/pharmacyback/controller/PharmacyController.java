package com.example.pharmacyback.controller;

import com.example.pharmacyback.repository.ManufacturerRepository;
import com.example.pharmacyback.repository.ProductRepository;
import com.example.pharmacyback.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class PharmacyController {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    public PharmacyController(ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping(value = "/getAllProducts")
    public List<Product> getAllProducts(HttpServletResponse response) throws IOException {

        List<Product> allProductsList = productRepository.findAll();
        if(allProductsList != null) {
            return allProductsList;
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }

    }

    @PostMapping(value = "/deleteProduct")
    public String getTest(HttpServletResponse response, @RequestBody Integer id) throws IOException {
        productRepository.deleteById(id);

        return "Sucessfull DELETE!";
    }

    @PostMapping(value = "/saveProduct")
    public String getTest(HttpServletResponse response, @RequestBody Product product) throws IOException {
        Product prod = productRepository.save(product);
        if(prod != null) {
            return "Sucessfull SAVE!";
        } else {
            response.sendError(401, "Trouble with getting list of all products!");
            return null;
        }
    }
}
