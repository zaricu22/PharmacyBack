package com.pharmacy.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "manufacturers", schema = "pharmacy")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

    public Manufacturer() {
    }

    public Manufacturer(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Manufacturer(UUID id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    private void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
