package com.learnspringboot.store.service.cart;

import com.learnspringboot.store.repository.CartRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.model.CartItem;
import com.learnspringboot.store.model.Product;
import com.learnspringboot.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice();
            cart.updateTotalAmount();
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.setTotalPrice();

            cart.addItem(newItem);
        }

        // Chỉ cần save Cart (cascade sẽ lo phần CartItem)
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        CartItem itemToRemove = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .ifPresentOrElse(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                }, () -> {
                    throw new ResourceNotFoundException("Product not found!");
                });

        cart.updateTotalAmount();

        cartRepository.save(cart);
    }

}
