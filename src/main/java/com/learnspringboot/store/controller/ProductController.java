package com.learnspringboot.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.dto.ProductDto;
import com.learnspringboot.store.request.AddProductRequest;
import com.learnspringboot.store.request.UpdateProductRequest;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.product.IProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Get all products successfully!", products));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getProducts(@RequestParam(required = false) String brand,
            @RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        List<ProductDto> products = productService.searchProducts(brand, name, category);
        return ResponseEntity.ok(new ApiResponse("Get all products successfully!", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Get product by ID successfully!", product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest request) {
        ProductDto product = productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse("Create product successfully!", product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductDto product = productService.updateProduct(id, request);
        return ResponseEntity.ok(new ApiResponse("Update product successfully!", product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/count")
    public ResponseEntity<ApiResponse> countProducts(@RequestParam String brand, @RequestParam String name) {
        Long count = productService.countProductsByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse("Count products by brand and name successfully!", count));
    }

}
