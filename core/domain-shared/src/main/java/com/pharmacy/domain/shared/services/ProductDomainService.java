package com.pharmacy.domain.shared.services;

import com.pharmacy.domain.exceptions.ErrorMessages;
import com.pharmacy.domain.exceptions.ProductExistsException;
import com.pharmacy.domain.exceptions.ProductNotFoundException;
import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.domain.model.Product;
import com.pharmacy.domain.repositories.ProductRepository;
import com.pharmacy.persistence.repositories.ProductRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductDomainService {

    private final ProductRepository productRepository;
    private final ManufacturerDomainService manufacturerDomainService;

    public ProductDomainService(ProductRepositoryImpl productRepositoryImpl, ManufacturerDomainService manufacturerDomainService) {
        this.productRepository = productRepositoryImpl;
        this.manufacturerDomainService = manufacturerDomainService;
    }

    public Product createByNameAndExpiryDate(String name, UUID manufacturerId, int price, Date expiryDate){
        int countExistingProduct = productRepository.countByNameAndExpiryDate(name, expiryDate);
        Manufacturer manufacturer = manufacturerDomainService.createExistById(manufacturerId);

        if (countExistingProduct > 0)
          throw new ProductExistsException(ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
        else {
            return new Product(
                    name,
                    manufacturer,
                    price,
                    expiryDate
            );
        }
    }

    public Product changeExist(UUID uuid, String name, UUID manufacturerId, int price, Date expiryDate){
        if(Objects.isNull(uuid))
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);

        boolean productExists = productRepository.existsById(uuid);
        if (!productExists)
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        Manufacturer manufacturer = manufacturerDomainService.createExistById(manufacturerId);

        return new Product(
                uuid,
                name,
                manufacturer,
                price,
                expiryDate
        );
    }
}
