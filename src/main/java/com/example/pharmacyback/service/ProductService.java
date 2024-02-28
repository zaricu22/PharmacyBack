package com.example.pharmacyback.service;

import com.example.pharmacyback.exceptions.ErrorMessages;
import com.example.pharmacyback.exceptions.errors.DateExpiredException;
import com.example.pharmacyback.exceptions.errors.ProductExistsException;
import com.example.pharmacyback.exceptions.errors.ProductNotFoundException;
import com.example.pharmacyback.exceptions.errors.WrongManudacturerException;
import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.repository.ManufacturerRepository;
import com.example.pharmacyback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductService(ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
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
        Date now = new Date();
        int countExistingProduct = productRepository.countByNameAndExpiryDate(name, expiryDate);
        boolean manufacturerExists = Objects.nonNull(product.getManufacturer().getId()) && manufacturerRepository.existsById(product.getManufacturer().getId());

        if (countExistingProduct > 0)
            throw new ProductExistsException(ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
        else if (!manufacturerExists)
            throw new WrongManudacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        else if(expiryDate.before(now))
            throw new DateExpiredException(ErrorMessages.DATE_EXPIRED_EXCEPTION);
        else
            return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Date expiryDate = product.getExpiryDate();
        Date now = new Date();
        boolean productExists = productRepository.existsById(product.getId());
        boolean manufacturerExists = manufacturerRepository.existsById(product.getManufacturer().getId());

        if (!manufacturerExists)
            throw new WrongManudacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        else if(expiryDate.before(now))
            throw new DateExpiredException(ErrorMessages.DATE_EXPIRED_EXCEPTION);
        else if (!productExists)
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        else
            return productRepository.save(product);
    }
}
