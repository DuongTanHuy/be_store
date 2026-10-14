package com.learnspringboot.store.service.cart;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.CartDto;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    private CartDto mapToCartDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        return mapToCartDto(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        cart.getItems().clear();
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Cart not found!");
                });
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        return newCart.getId();
    }

}
