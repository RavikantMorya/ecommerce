package com.maurya.ecommerce.service;

import com.maurya.ecommerce.dtos.OrderItemDTO;
import com.maurya.ecommerce.dtos.OrderResponse;
import com.maurya.ecommerce.model.*;
import com.maurya.ecommerce.repository.CartRepository;
import com.maurya.ecommerce.repository.OrderRepository;
import com.maurya.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        //validate for Cart items
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty())
            return Optional.empty();

        //validate for user
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return Optional.empty();
        User user = userOpt.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        //Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalPrice);


        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        null,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                )).toList();
        order.setOrderItems(orderItems);

        //Create Order
        Order savedOrder = orderRepository.save(order);
        //Clear the cart
        cartService.clearCart(userId);

        return Optional.of(maptoOrderResponse(savedOrder));
    }

    private OrderResponse maptoOrderResponse(Order savedOrder) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(savedOrder.getId());
        orderResponse.setTotalAmount(savedOrder.getTotalAmount());
        orderResponse.setOrderStatus(savedOrder.getOrderStatus());
        orderResponse.setCreatedAt(savedOrder.getCreatedAt());
        orderResponse.setOrderItems(savedOrder.getOrderItems()
                        .stream()
                        .map(this::mapToOrderItemDTO)
                        .toList());
        return orderResponse;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(Long.valueOf(orderItem.getQuantity()));
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setSubtotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return orderItemDTO;
    }
}
