package com.learnspringboot.store.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.ProductDto;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Category;
import com.learnspringboot.store.model.Product;
import com.learnspringboot.store.repository.CategoryRepository;
import com.learnspringboot.store.repository.ProductRepository;
import com.learnspringboot.store.request.AddProductRequest;
import com.learnspringboot.store.request.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto mapToProductDto(Product product) {
        // ProductDto productDto = new ProductDto();
        // productDto.setId(product.getId());
        // productDto.setName(product.getName());
        // productDto.setBrand(product.getBrand());
        // productDto.setPrice(product.getPrice());
        // productDto.setInventory(product.getInventory());
        // productDto.setDescription(product.getDescription());

        // if (product.getImages() != null) {
        // List<ImageDto> imageDtos = new ArrayList<>();
        // for (Image image : product.getImages()) {
        // ImageDto imageDto = new ImageDto();
        // imageDto.setImageId(image.getId());
        // imageDto.setImageName(image.getFileName());
        // imageDto.setDownloadUrl(image.getDownloadUrl());
        // imageDtos.add(imageDto);
        // }
        // productDto.setImages(imageDtos);
        // }

        // if (product.getCategory() != null) {
        // CategoryDto categoryDto = new CategoryDto();
        // categoryDto.setId(product.getCategory().getId());
        // categoryDto.setName(product.getCategory().getName());
        // productDto.setCategory(categoryDto);
        // }

        // return productDto;

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public List<ProductDto> mapToListProductDto(List<Product> products) {
        return products.stream().map(this::mapToProductDto).toList();
    }

    private Category handleCategory(String categoryName) {
        return Optional.ofNullable(categoryRepository.findByName(categoryName))
                .orElseGet(() -> {
                    Category newCategory = new Category(categoryName);
                    return categoryRepository.save(newCategory);
                });
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = handleCategory(request.getCategory());

        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public ProductDto createProduct(AddProductRequest request) {
        Category category = handleCategory(request.getCategory());
        // request.setCategory(category);
        Product product = productRepository.save(createProduct(request, category));

        return mapToProductDto(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        return mapToProductDto(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ResourceNotFoundException("Product not found!");
        });
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                // .map(product -> productRepository.save(product)) // tương tự cách viết trên
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        return mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = mapToListProductDto(products);

        return productDtos;
    }

    @Override
    public List<ProductDto> searchProducts(String brand, String name, String category) {
        List<Product> products = productRepository.searchProducts(brand, name, category);

        List<ProductDto> productDtos = mapToListProductDto(products);

        return productDtos;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

}
