package com.example.pharmacyback.repository;

import com.example.pharmacyback.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {
    List<Manufacturer> findAll();

    @Query(value = "" +
            "SELECT m.name, count(m.name) \n" +
            "FROM pharmacy.manufacturers m \n" +
            "LEFT JOIN pharmacy.products p \n" +
            "ON p.manufacturer = m.id \n" +
            "GROUP BY m.name"
        , nativeQuery = true)
    List<Map<String, Integer>> countManufacturersProducts();

}
