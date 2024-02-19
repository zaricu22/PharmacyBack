package com.example.pharmacyback.service;

import com.example.pharmacyback.repository.ProductRepository;
import com.example.pharmacyback.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    void deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            return null;
        }
    }
}
