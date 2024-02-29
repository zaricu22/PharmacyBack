package com.example.pharmacyback.dto;

import com.example.pharmacyback.model.Product;
import java.util.List;
import java.util.UUID;

public record ManuWithProdDTO(
        UUID id,
        String name,
        List<Product> products
) {
}
