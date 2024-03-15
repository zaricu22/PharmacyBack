package com.pharmacy.application.contracts.dtos;

import java.util.List;
import java.util.UUID;

public record ManufacturerProductsDTO(UUID id, String name, List<ProductDTO> products) {
}
