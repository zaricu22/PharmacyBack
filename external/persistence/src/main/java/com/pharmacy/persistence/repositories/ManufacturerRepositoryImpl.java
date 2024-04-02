package com.pharmacy.persistence.repositories;

import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManufacturerRepositoryImpl extends JpaRepository<Manufacturer, UUID>, ManufacturerRepository {

    @Override
    List<Manufacturer> findAll();

    @Override
    Optional<Manufacturer> findById(UUID id);
}
