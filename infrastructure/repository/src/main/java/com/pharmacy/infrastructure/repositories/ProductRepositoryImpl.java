package com.pharmacy.infrastructure.repositories;

import com.pharmacy.domain.model.Product;
import com.pharmacy.domain.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryImpl extends JpaRepository<Product, UUID>, ProductRepository {
    @Override
    List<Product> findAll();

    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    Optional<Product> findById(UUID id);

    @Override
    void deleteById(UUID id);

    @Override
    boolean existsById(UUID id);

    @Override
    Product save(Product entity);

    @Query(
            value =
                    ""
                            + "SELECT count(p.name) "
                            + "FROM Pharmacy.Products p "
                            + "WHERE p.name = :name AND p.expiry_date = :expiryDate",
            nativeQuery = true)
    int countByNameAndExpiryDate(String name, Date expiryDate);

    @Query(
            value = "" + "SELECT * " + "FROM Pharmacy.Products p " + "ORDER BY p.price ASC " + "LIMIT 5",
            nativeQuery = true)
    List<Product> findFirstFiveOrderByPriceAsc();

    @Query(
            value = "" + "SELECT * " + "FROM Pharmacy.Products p " + "ORDER BY p.price DESC " + "LIMIT 5",
            nativeQuery = true)
    List<Product> findFirstFiveOrderByPriceDesc();
}
