package com.example.pharmacyback.controllers;

import com.example.pharmacyback.crud.ManufacturerRepository;
import com.example.pharmacyback.crud.ProductRepository;
import com.example.pharmacyback.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class PharmacyController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

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
}
