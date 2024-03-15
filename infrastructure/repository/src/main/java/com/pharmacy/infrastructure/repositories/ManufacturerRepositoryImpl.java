package com.pharmacy.infrastructure.repositories;

import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ManufacturerRepositoryImpl extends JpaRepository<Manufacturer, UUID>, ManufacturerRepository {

    List<Manufacturer> findAll();
}
