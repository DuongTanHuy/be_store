package com.learnspringboot.store.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private List<ImageDto> productImages;
    private CategoryDto productCategory;
    private String productBrand;
    private String productDescription;
    // private ProductDto product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
