package com.pharmacy.application.contracts.dtos;

import java.util.Date;

public record UpdateProductDTO(String name, ManufacturerDTO manufacturer, int price, Date expiryDate) {
}
