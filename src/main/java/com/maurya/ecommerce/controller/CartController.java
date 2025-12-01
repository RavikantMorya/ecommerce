package com.maurya.ecommerce.controller;

import com.maurya.ecommerce.dtos.CartItemRequest;
import com.maurya.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addCart(@RequestHeader("X-USER-ID") String userId, @RequestBody CartItemRequest cartItemRequest)
    {

       if (cartService.addCart(userId,cartItemRequest))
           return ResponseEntity.status(HttpStatus.CREATED).build();
       return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not Found!");

    }
}
