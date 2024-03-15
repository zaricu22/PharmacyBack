package com.pharmacy.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
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
    int price;

    @Temporal(TemporalType.TIMESTAMP)
    @Future
    private Date expiryDate;

    public Product() {
    }

    public Product(UUID id, String name, Manufacturer manufacturer, int price, Date expiryDate) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.expiryDate = expiryDate;
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

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
