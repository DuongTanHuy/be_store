package com.learnspringboot.store.service.product;

import java.util.List;

import com.learnspringboot.store.dto.ProductDto;
import com.learnspringboot.store.model.Product;
import com.learnspringboot.store.request.AddProductRequest;
import com.learnspringboot.store.request.UpdateProductRequest;

public interface IProductService {
    ProductDto createProduct(AddProductRequest request);

    ProductDto updateProduct(Long productId, UpdateProductRequest request);

    ProductDto getProductById(Long id);

    void deleteProductById(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> searchProducts(String brand, String name, String category);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
