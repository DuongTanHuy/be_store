package com.learnspringboot.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.store.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
