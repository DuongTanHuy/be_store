package com.learnspringboot.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
