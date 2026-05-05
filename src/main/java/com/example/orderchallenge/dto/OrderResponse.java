package com.example.orderchallenge.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String customerCode,
        List<String> productCodes,
        BigDecimal totalValue,
        String deliveryAddress,
        String status,
        LocalDateTime createdAt
) {
}