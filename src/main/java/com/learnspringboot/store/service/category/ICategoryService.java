package com.learnspringboot.store.service.category;

import java.util.List;

import com.learnspringboot.store.dto.CategoryDto;
import com.learnspringboot.store.model.Category;

public interface ICategoryService {
    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(Category category);

    CategoryDto updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
