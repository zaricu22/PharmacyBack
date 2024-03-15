package com.pharmacy.application.contracts.mappers;

import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.domain.model.Product;

import java.util.List;

public interface ProductMapper {
    Product toEntity(ProductDTO productDTO);

    List<Product> toEntity(List<ProductDTO> productDTOList);

    ProductDTO toDto(Product product);

    List<ProductDTO> toDto(List<Product> productList);

}
