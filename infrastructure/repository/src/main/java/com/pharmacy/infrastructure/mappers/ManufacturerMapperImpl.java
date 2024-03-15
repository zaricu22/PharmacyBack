package com.pharmacy.infrastructure.mappers;

import com.pharmacy.application.contracts.dtos.ManufacturerDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsCountDTO;
import com.pharmacy.application.contracts.dtos.ManufacturerProductsDTO;
import com.pharmacy.application.contracts.mappers.ManufacturerMapper;
import com.pharmacy.domain.model.Manufacturer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ManufacturerMapperImpl extends ManufacturerMapper {
    @Override
    Manufacturer toEntity(ManufacturerDTO manufacturerDTO);

    @Override
    List<Manufacturer> toEntity(List<ManufacturerDTO> manufacturerList);

    @Override
    ManufacturerDTO toDto(Manufacturer manufacturer);

    @Override
    List<ManufacturerDTO> toDto(List<Manufacturer> manufacturerList);

    @Override
    List<ManufacturerProductsDTO> mapToManufacturerProductsList(List<Manufacturer> manufacturers);

    @Override
    List<ManufacturerProductsCountDTO> mapToProductsCountList(List<Manufacturer> manufacturers);

    default ManufacturerProductsCountDTO mapToProductsCount(Manufacturer manufacturer) {
        ManufacturerProductsCountDTO manufacturerProductsCountDTO = new ManufacturerProductsCountDTO(
                manufacturer.getName(),
                manufacturer.getProducts().size()
        );
        return manufacturerProductsCountDTO;
    }

//    @Mapping(source = "products", target = "count", qualifiedByName = "inchToCentimeter")
//    ManufacturerProductsCountDTO mapToProductsCount(Manufacturer manufacturers);
//
//    @Named("inchToCentimeter")
//    static int countProducts(List<Product> products) {
//        return products.size();
//    }
}
