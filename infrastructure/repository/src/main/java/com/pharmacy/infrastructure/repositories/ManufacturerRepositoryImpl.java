package com.pharmacy.infrastructure.repositories;

import com.pharmacy.application.contracts.dtos.CountManuProdsDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import com.pharmacy.domain.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ManufacturerRepositoryImpl extends JpaRepository<Manufacturer, UUID>, ManufacturerRepository {
    List<Manufacturer> findAll();

    @Query(value = "SELECT new com.pharmacy.application.contracts.dtos.CountManuProdsDTO(m.name, count(m.name)) \n"
                    + "FROM Manufacturer m \n"
                    + "LEFT JOIN Product p \n"
                    + "ON p.manufacturer.id = m.id \n"
                    + "GROUP BY m.name")
    List<CountManuProdsDTO> countManufacturersProducts();

    @Query(value = "SELECT new com.pharmacy.application.contracts.dtos.ManufacturerDTO(m.id, m.name, m.products) \n"
                    + "FROM Manufacturer m \n"
                    + "LEFT JOIN Product p \n"
                    + "ON p.manufacturer.id = m.id \n"
                    + "GROUP BY m.name")
    List<ManufacturerDTO> getManufacturersWithProducts();
}
