package com.learnspringboot.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
