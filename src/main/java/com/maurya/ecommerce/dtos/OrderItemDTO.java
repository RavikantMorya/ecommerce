package com.maurya.ecommerce.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private Long id;
    private Long productId;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
