package com.learnspringboot.store.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private Long productId;
    private String productName;
    private List<ImageDto> productImages;
    private CategoryDto productCategory;
    private String productBrand;
    private String productDescription;
}
