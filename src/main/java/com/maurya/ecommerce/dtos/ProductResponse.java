package com.maurya.ecommerce.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private Boolean active;
    private Integer quantity;
}
