package com.learnspringboot.store.service.cart;

import java.math.BigDecimal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.CartDto;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.model.User;
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
    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        return mapToCartDto(cart);
    }

    @Override
    public void clearCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        cart.getItems().clear();
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        User user = cart.getUser();
        if (user != null) {
            user.setCart(null);
        }
        cart.setUser(null);

        cartRepository.delete(cart);
    }

    @Override
    public BigDecimal getTotalPriceById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {

        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public Cart getCartByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

}
