package com.maurya.ecommerce.controller;

import com.maurya.ecommerce.dtos.CartItemRequest;
import com.maurya.ecommerce.dtos.CartItemResponse;
import com.maurya.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Transactional
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addCart(@RequestHeader("X-USER-ID") String userId, @RequestBody CartItemRequest cartItemRequest)
    {

       if (cartService.addCart(userId,cartItemRequest))
           return ResponseEntity.status(HttpStatus.CREATED).build();
       return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not Found!");

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-USER-ID") String userId,
                                               @PathVariable String productId)
    {

       boolean deleted = cartService.removeItemFromCart(userId,productId);
       if (deleted)
            return ResponseEntity.noContent().build();
       return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestHeader("X-USER-ID") String userId)
    {
       return ResponseEntity.ok(cartService.fetchCartItems(userId));
    }

}
