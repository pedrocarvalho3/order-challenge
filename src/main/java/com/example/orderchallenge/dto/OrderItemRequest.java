package com.example.orderchallenge.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderItemRequest(
        @NotBlank(message = "Product code is required")
        String productCode
) {
}