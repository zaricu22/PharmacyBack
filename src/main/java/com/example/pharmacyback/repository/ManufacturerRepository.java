package com.example.pharmacyback.repository;

import com.example.pharmacyback.dto.CountManuProdDTO;
import com.example.pharmacyback.dto.ManuWithProdDTO;
import com.example.pharmacyback.model.Manufacturer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {
  List<Manufacturer> findAll();

  @Query(
      value =
          ""
              + "SELECT new com.example.pharmacyback.dto.CountManuProdDTO(m.name, count(m.name)) \n"
              + "FROM Manufacturer m \n"
              + "LEFT JOIN Product p \n"
              + "ON p.manufacturer.id = m.id \n"
              + "GROUP BY m.name")
  List<CountManuProdDTO> countManufacturersProducts();

  @Query(
      value =
          ""
              + "SELECT new com.example.pharmacyback.dto.ManuWithProdDTO(m.id, m.name, m.products) \n"
              + "FROM Manufacturer m \n"
              + "LEFT JOIN Product p \n"
              + "ON p.manufacturer.id = m.id \n"
              + "GROUP BY m.name")
  List<ManuWithProdDTO> getManufacturersWithProducts();
}
