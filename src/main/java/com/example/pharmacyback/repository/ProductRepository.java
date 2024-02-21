package com.example.pharmacyback.repository;

import com.example.pharmacyback.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findAll();

    Product findProductById(UUID id);

    void deleteById(UUID id);

    @Override
    Product save(Product entity);
}
