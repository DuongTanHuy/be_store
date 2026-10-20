package com.learnspringboot.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.dto.CategoryDto;
import com.learnspringboot.store.model.Category;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return ResponseEntity.ok().body(new ApiResponse("Get all categories successfully!", categoryDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Get category successfully!", categoryDto));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name) {
        CategoryDto categoryDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("Get category successfully!", categoryDto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        CategoryDto newCategoryDto = categoryService.createCategory(category);
        return ResponseEntity.ok(new ApiResponse("Create category successfully!", newCategoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        CategoryDto updatedCategoryDto = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse("Update category successfully!", updatedCategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
