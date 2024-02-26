package com.example.pharmacyback.repository;

import com.example.pharmacyback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();

    @Query(value = "" +
            "SELECT count(p.name) " +
            "FROM Pharmacy.Products p " +
            "WHERE p.name = :name AND p.expiry_date = :expiryDate"
            , nativeQuery = true)
    int countByNameAndExpiryDate(String name, Date expiryDate);

    Optional<Product> findProductById(UUID id);

    void deleteById(UUID id);

    @Override
    Product save(Product entity);

    @Query(value = "" +
            "SELECT * " +
            "FROM Pharmacy.Products p " +
            "ORDER BY p.price ASC " +
            "LIMIT 5"
        , nativeQuery = true)
    List<Product> findAllOrderByPriceAsc();

    @Query(value = "" +
            "SELECT * " +
            "FROM Pharmacy.Products p " +
            "ORDER BY p.price DESC " +
            "LIMIT 5"
        , nativeQuery = true)
    List<Product> findAllOrderByPriceDesc();

}
