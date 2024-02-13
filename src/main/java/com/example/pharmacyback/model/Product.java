package com.example.pharmacyback.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "products", schema = "pharmacy")
public class Product {

    @Id
    int id;
    String name;
    @ManyToOne
    @JoinColumn(name="manufacturer", referencedColumnName="id")
    Manufacturer manufacturer;
    int price;
    @Temporal(TemporalType.TIMESTAMP)
    Date expiryDate;

    public Product() {
    }

    public Product(int id, String name, Manufacturer manufacturer, int price, Date expiryDate) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                ", price=" + price +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
