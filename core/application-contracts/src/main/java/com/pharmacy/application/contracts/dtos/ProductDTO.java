package com.pharmacy.application.contracts.dtos;

import java.util.Date;
import java.util.UUID;

public record ProductDTO(UUID id, String name, ManufacturerDTO manufacturer, int price, Date expiryDate) {
}
