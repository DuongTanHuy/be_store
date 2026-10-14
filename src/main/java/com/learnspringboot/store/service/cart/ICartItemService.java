package com.learnspringboot.store.service.cart;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateQuantity(Long cartId, Long productId, int quantity);

}
