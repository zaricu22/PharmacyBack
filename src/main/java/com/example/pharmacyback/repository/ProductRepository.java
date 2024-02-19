package com.example.pharmacyback.repository;

import com.example.pharmacyback.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    @Override
    void deleteById(Integer id);

    @Override
    Product save(Product entity);
}
