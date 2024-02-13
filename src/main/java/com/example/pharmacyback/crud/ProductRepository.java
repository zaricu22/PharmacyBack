package com.example.pharmacyback.crud;

import com.example.pharmacyback.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();
}
