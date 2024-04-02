package com.pharmacy.application.contracts.mappers;

import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsCountDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsDTO;

import java.util.List;

public interface ManufacturerMapper {

    ManufacturerDTO toDto(Manufacturer manufacturer);

    List<ManufacturerDTO> toDto(List<Manufacturer> manufacturerList);

    List<ManufacturerProductsDTO> mapToManufacturerProductsList(List<Manufacturer> manufacturers);

    List<ManufacturerProductsCountDTO> mapToProductsCountList(List<Manufacturer> manufacturers);
}
