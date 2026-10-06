package com.learnspringboot.store.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String category;
}
