package com.example.orderchallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Customer code is required")
        String customerCode,

        @NotEmpty(message = "Order must contain at least one item")
        List<@Valid OrderItemRequest> items,

        @NotNull(message = "Total value is required")
        @DecimalMin(value = "0.01", message = "Total value must be greater than zero")
        BigDecimal totalValue,

        @NotBlank(message = "Delivery address is required")
        String deliveryAddress
) {
}