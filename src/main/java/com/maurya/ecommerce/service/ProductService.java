package com.maurya.ecommerce.service;

import com.maurya.ecommerce.dtos.ProductRequest;
import com.maurya.ecommerce.dtos.ProductResponse;
import com.maurya.ecommerce.model.Product;
import com.maurya.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapProductFromProductRequest(product,productRequest);
        productRepository.save(product);
        return mapToProductResponse(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(product.getCategory());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        return productResponse;
    }

    private void mapProductFromProductRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(productRequest.getCategory());
        product.setQuantity(productRequest.getQuantity());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

        return productRepository.findById(id).map(
                existing ->
                {
                    mapProductFromProductRequest(existing, productRequest);
                    Product updatedProduct = productRepository.save(existing);
                    return mapToProductResponse(updatedProduct);
                });
    }
}
