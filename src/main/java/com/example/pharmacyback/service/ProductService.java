package com.example.pharmacyback.service;

import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Product getProductById(UUID uuid) {
        try {
            return productRepository.findProductById(uuid);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Product> getTopFiveProductByPrice() {
        try {
            return productRepository.findAllOrderByPriceDesc();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Product> getLeastFiveProductByPrice() {
        try {
            return productRepository.findAllOrderByPriceAsc();
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    public Product saveProduct(Product product) {
        try {
            System.out.println(product);
            return productRepository.save(product);
        } catch (Exception e) {
            return null;
        }
    }
}
