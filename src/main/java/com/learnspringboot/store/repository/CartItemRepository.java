package com.learnspringboot.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public void deleteAllByCartId(Long id);
}
