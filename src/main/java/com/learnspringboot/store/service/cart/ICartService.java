package com.learnspringboot.store.service.cart;

import java.math.BigDecimal;

import com.learnspringboot.store.dto.CartDto;

public interface ICartService {
    CartDto getCart(Long id);

    void clearCart(Long id);

    void deleteCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

}
