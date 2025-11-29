package com.maurya.ecommerce.controller;

import com.maurya.ecommerce.dtos.ProductRequest;
import com.maurya.ecommerce.dtos.ProductResponse;
import com.maurya.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest)
    {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest)
    {
            return productService.updateProduct(id, productRequest)
                    .map(ResponseEntity::ok)
                    .orElseGet(()->ResponseEntity.notFound().build());
    }


}
