package com.maurya.ecommerce.dtos;

import com.maurya.ecommerce.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime createdAt;

}
