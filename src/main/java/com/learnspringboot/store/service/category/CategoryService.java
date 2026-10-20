package com.learnspringboot.store.service.category;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.CategoryDto;
import com.learnspringboot.store.exceptions.AlreadyExistsException;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Category;
import com.learnspringboot.store.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private CategoryDto mapToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private List<CategoryDto> mapToCategoryDtoList(List<Category> categories) {
        return categories.stream().map(this::mapToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        return mapToCategoryDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);

        return mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return mapToCategoryDtoList(categories);
    }

    @Override
    public CategoryDto createCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(cate -> {
                    Category newCategory = categoryRepository.save(cate);

                    return mapToCategoryDto(newCategory);
                })
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists!"));
    }

    @Override
    public CategoryDto updateCategory(Long id, Category category) {
        return Optional
                .ofNullable(categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found!")))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    Category updatedCategory = categoryRepository.save(oldCategory);

                    return mapToCategoryDto(updatedCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Category not found!");
                        });
    }

}
