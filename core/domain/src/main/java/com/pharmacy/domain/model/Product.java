package com.pharmacy.domain.model;

import com.pharmacy.domain.exceptions.CustomArgumentException;
import com.pharmacy.domain.exceptions.ErrorMessages;
import com.pharmacy.domain.exceptions.ProductDateExpiredException;
import com.pharmacy.domain.exceptions.WrongManufacturerException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products", schema = "pharmacy")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @Min(value = 0)
    private int price;

    @Temporal(TemporalType.TIMESTAMP)
    @Future
    private Date expiryDate;

    public Product() {}

    public Product(String name, Manufacturer manufacturer, int price, Date expiryDate) {
        if(Objects.isNull(name) || name.isEmpty())
            throw new CustomArgumentException(ErrorMessages.EMPTY_NAME_EXCEPTION);
        if(Objects.isNull(manufacturer))
            throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        if(price < 0)
            throw new CustomArgumentException(ErrorMessages.NEGATIVE_PRICE_EXCEPTION);
        if(Objects.isNull(name) || expiryDate.before(new Date()))
            throw new ProductDateExpiredException(ErrorMessages.EXPIRED_DATE_EXCEPTION);

        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    // Own-created annotation prevent MapStruct from using Setters
    @Default
    public Product(UUID id, String name, Manufacturer manufacturer, int price, Date expiryDate) {
        this(name, manufacturer, price, expiryDate);

        if (Objects.isNull(id))
            throw new CustomArgumentException(ErrorMessages.BAD_ID_EXCEPTION);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    private void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    private void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && id.equals(product.id) && name.equals(product.name) && manufacturer.equals(product.manufacturer) && expiryDate.equals(product.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacturer, price, expiryDate);
    }

    @Override
    public String toString() {
        return "Product{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", manufacturer="
                + manufacturer
                + ", price="
                + price
                + ", expiryDate="
                + expiryDate
                + '}';
    }
}
