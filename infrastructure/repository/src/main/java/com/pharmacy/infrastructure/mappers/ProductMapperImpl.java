package com.pharmacy.infrastructure.mappers;

import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.application.contracts.mappers.ProductMapper;
import com.pharmacy.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapperImpl extends ProductMapper {

    @Override
    Product toEntity(ProductDTO productDTO);

    @Override
    List<Product> toEntity(List<ProductDTO> productDTOList);

    @Override
    ProductDTO toDto(Product product);

    @Override
    List<ProductDTO> toDto(List<Product> productList);

}
