package com.example.pharmacyback.services;

import com.example.pharmacyback.crud.ProductRepository;
import com.example.pharmacyback.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
