package com.learnspringboot.store.service.cart;

import java.math.BigDecimal;

import com.learnspringboot.store.dto.CartDto;
import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.model.User;

public interface ICartService {
    CartDto getCartById(Long id);

    void clearCartById(Long id);

    void deleteCartById(Long id);

    BigDecimal getTotalPriceById(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long id);

}
