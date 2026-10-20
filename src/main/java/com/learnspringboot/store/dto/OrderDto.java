package com.learnspringboot.store.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.learnspringboot.store.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> items;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
}
