package com.learnspringboot.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
