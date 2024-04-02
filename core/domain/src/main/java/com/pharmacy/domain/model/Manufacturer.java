package com.pharmacy.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pharmacy.domain.exceptions.CustomArgumentException;
import com.pharmacy.domain.exceptions.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;
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

    public Manufacturer() {}

    public Manufacturer(String name) {
        if(Objects.isNull(name) || name.isEmpty())
            throw new CustomArgumentException(ErrorMessages.EMPTY_NAME_EXCEPTION);

        this.name = name;
    }

    public Manufacturer(UUID id, String name) {
        this(name);

        if (Objects.isNull(id))
            throw new CustomArgumentException(ErrorMessages.BAD_ID_EXCEPTION);
        this.id = id;
    }

    // Own-created annotation prevent MapStruct from using Setters
    @Default
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
