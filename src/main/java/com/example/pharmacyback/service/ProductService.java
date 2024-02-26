package com.example.pharmacyback.service;

import com.example.pharmacyback.exceptions.ErrorMessages;
import com.example.pharmacyback.exceptions.errors.ProductExistsException;
import com.example.pharmacyback.exceptions.errors.ProductNotFoundException;
import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID uuid) {
        return productRepository.findProductById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION));
    }

    public List<Product> getTopFiveProductByPrice() {
        return productRepository.findAllOrderByPriceDesc();
    }

    public List<Product> getLeastFiveProductByPrice() {
        return productRepository.findAllOrderByPriceAsc();
    }

    public void deleteProduct(UUID id) {
        boolean prodExists = productRepository.existsById(id);
        if(prodExists)
            productRepository.deleteById(id);
        else
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
    }

    public Product insertProduct(Product product) {
        String name = product.getName();
        Date expiryDate = product.getExpiryDate();
        int countExisting = productRepository.countByNameAndExpiryDate(name, expiryDate);
        if(countExisting == 0)
            return productRepository.save(product);
        else
            throw new ProductExistsException(ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
    }

    public Product updateProduct(Product product) {
        boolean prodExists = productRepository.existsById(product.getId());
        if(prodExists)
            return productRepository.save(product);
        else
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
    }
}
