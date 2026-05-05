package com.example.orderchallenge.controller;

import com.example.orderchallenge.dto.CreateOrderRequest;
import com.example.orderchallenge.dto.OrderResponse;
import com.example.orderchallenge.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody @Valid CreateOrderRequest request) {
        return orderService.create(request);
    }
}