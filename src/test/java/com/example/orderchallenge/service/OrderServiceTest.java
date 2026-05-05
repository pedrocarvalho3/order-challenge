package com.example.orderchallenge.service;

import com.example.orderchallenge.dto.CreateOrderRequest;
import com.example.orderchallenge.dto.OrderItemRequest;
import com.example.orderchallenge.entity.Order;
import com.example.orderchallenge.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderMessage() {
        CreateOrderRequest request = new CreateOrderRequest(
                "CLI-001",
                List.of(new OrderItemRequest("PROD-001")),
                BigDecimal.valueOf(100),
                "Rua Teste, 123"
        );

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = orderService.create(request);

        assertNotNull(response);
        assertEquals("CLI-001", response.customerCode());
        assertEquals(List.of("PROD-001"), response.productCodes());
        assertEquals(BigDecimal.valueOf(100), response.totalValue());
        assertEquals("Rua Teste, 123", response.deliveryAddress());
        assertEquals("CREATED", response.status());
        assertNotNull(response.createdAt());

        verify(orderRepository).save(any(Order.class));
    }
}
