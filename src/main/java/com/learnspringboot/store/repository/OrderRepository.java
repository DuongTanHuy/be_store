package com.learnspringboot.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}

// findBy: có thể có hoặc không
// getBy: chắc chắn có