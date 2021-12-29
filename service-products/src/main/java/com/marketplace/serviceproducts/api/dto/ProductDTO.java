package com.marketplace.serviceproducts.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private long store;
    private String name;
    private BigDecimal price;
    private boolean discontinued;
    private String description;
}
