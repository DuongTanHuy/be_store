package com.learnspringboot.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.repository.CartRepository;
import com.learnspringboot.store.request.AddCartItemRequest;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.cart.ICartItemService;
import com.learnspringboot.store.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts/{cartId}/items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> addCartItem(
            @PathVariable Long cartId,
            @RequestBody AddCartItemRequest request) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            cartId = cartService.initializeNewCart();
        }
        cartItemService.addItemToCart(cartId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(new ApiResponse("Add cart item successfully!", null));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId, @PathVariable Long productId,
            @RequestParam int quantity) {
        cartItemService.updateQuantity(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Update cart item successfully!", null));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartId, @PathVariable Long productId) {
        cartItemService.removeItemFromCart(cartId, productId);

        return ResponseEntity.ok(new ApiResponse("Delete cart item successfully!", null));
    }

}
