package com.pharmacy.application.contracts.dtos;

import com.pharmacy.domain.model.Product;

import java.util.List;
import java.util.UUID;

public record ManufacturerDTO(UUID id, String name, List<Product> products) {
}
