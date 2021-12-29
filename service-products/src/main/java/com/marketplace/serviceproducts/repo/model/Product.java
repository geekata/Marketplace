package com.marketplace.serviceproducts.repo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public final class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long store;
    private String name;
    private BigDecimal price;
    private boolean discontinued;
    private String description;

    public Product() {
    }

    public Product(long store, String name, BigDecimal price, boolean discontinued, String description) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.discontinued = discontinued;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getStore() {
        return store;
    }

    public void setStore(long store) {
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
