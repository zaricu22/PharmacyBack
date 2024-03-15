package com.pharmacy.domain.repositories;

import com.pharmacy.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);

    Product save(Product entity);

    int countByNameAndExpiryDate(String name, Date expiryDate);

    List<Product> findFirstFiveOrderByPriceAsc();

    List<Product> findFirstFiveOrderByPriceDesc();
}
