package com.learnspringboot.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.dto.CartDto;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts/{id}")
public class CartController {
    private final ICartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        CartDto cartDto = cartService.getCartById(id);
        return ResponseEntity.ok(new ApiResponse("Get cart successfully!", cartDto));
    }

    @GetMapping("/total")
    public ResponseEntity<ApiResponse> getTotalPriceById(@PathVariable Long id) {
        BigDecimal totalPrice = cartService.getTotalPriceById(id);
        return ResponseEntity.ok(new ApiResponse("Get total price successfully!", totalPrice));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCartById(@PathVariable Long id) {
        cartService.clearCartById(id);
        return ResponseEntity.ok(new ApiResponse("Clear successfully!", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Long id) {
        cartService.deleteCartById(id);
        return ResponseEntity.ok(new ApiResponse("Delete successfully!", null));
    }

}
