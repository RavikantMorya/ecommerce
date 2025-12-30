package com.maurya.ecommerce.service;

import com.maurya.ecommerce.dtos.CartItemRequest;
import com.maurya.ecommerce.dtos.CartItemResponse;
import com.maurya.ecommerce.model.CartItem;
import com.maurya.ecommerce.model.Product;
import com.maurya.ecommerce.model.User;
import com.maurya.ecommerce.repository.CartRepository;
import com.maurya.ecommerce.repository.ProductRepository;
import com.maurya.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public boolean addCart(String userId, CartItemRequest cartItemRequest) {
        Optional<User> userOpt  = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;
        User user = userOpt.get();
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if (productOpt.isEmpty())
            return false;
        if (productOpt.get().getQuantity() < cartItemRequest.getQuantity())
            return false;
        Product product = productOpt.get();

        CartItem existingCartItem = cartRepository.findByUserAndProduct(user,product);
        if (existingCartItem != null)
        {
            //update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartRepository.save(existingCartItem);
        }else {
            //create a cart
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartRepository.save(cartItem);
        }

        return true;

    }

    public boolean removeItemFromCart(String userId, String productId) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));
        if (userOpt.isPresent() && productOpt.isPresent()) {
            cartRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(String userId)
    {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isPresent()){
            User user = userOpt.get();
            return cartRepository.findAllByUser(user);
        }
        return List.of();
    }

    public List<CartItemResponse> fetchCartItems(String userId) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        return userOpt.map(user -> cartRepository.findAllByUser(user)
                .stream()
                .map(this::mapToCarResponse)
                .collect(Collectors.toList())).orElseGet(List::of);
    }

    private CartItemResponse mapToCarResponse(CartItem cartItem) {

        CartItemResponse  response= new CartItemResponse();
        response.setProduct(cartItem.getProduct());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(cartItem.getPrice());
        response.setCreatedAt(cartItem.getCreatedAt());
        response.setUpdatedAt(cartItem.getUpdatedAt());
        return response;
    }


    public void clearCart(String userId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        userOpt.ifPresent(cartRepository::deleteByUser);
    }
}
