package com.example.orderchallenge.service;

import com.example.orderchallenge.dto.CreateOrderRequest;
import com.example.orderchallenge.dto.OrderItemRequest;
import com.example.orderchallenge.entity.Order;
import com.example.orderchallenge.entity.OrderItem;
import com.example.orderchallenge.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Test
    void shouldFindAllOrders() {
        LocalDateTime firstCreatedAt = LocalDateTime.of(2026, 5, 5, 10, 0);
        LocalDateTime secondCreatedAt = LocalDateTime.of(2026, 5, 5, 11, 0);

        Order firstOrder = Order.builder()
                .customerCode("CLI-001")
                .totalValue(BigDecimal.valueOf(100))
                .deliveryAddress("Rua Teste, 123")
                .status("CREATED")
                .createdAt(firstCreatedAt)
                .build();
        firstOrder.addItem(OrderItem.builder().productCode("PROD-001").build());

        Order secondOrder = Order.builder()
                .customerCode("CLI-002")
                .totalValue(BigDecimal.valueOf(200))
                .deliveryAddress("Avenida Teste, 456")
                .status("CREATED")
                .createdAt(secondCreatedAt)
                .build();
        secondOrder.addItem(OrderItem.builder().productCode("PROD-002").build());

        when(orderRepository.findAll()).thenReturn(List.of(firstOrder, secondOrder));

        var responses = orderService.findAll();

        assertEquals(2, responses.size());

        assertEquals("CLI-001", responses.get(0).customerCode());
        assertEquals(List.of("PROD-001"), responses.get(0).productCodes());
        assertEquals(BigDecimal.valueOf(100), responses.get(0).totalValue());
        assertEquals("Rua Teste, 123", responses.get(0).deliveryAddress());
        assertEquals("CREATED", responses.get(0).status());
        assertEquals(firstCreatedAt, responses.get(0).createdAt());

        assertEquals("CLI-002", responses.get(1).customerCode());
        assertEquals(List.of("PROD-002"), responses.get(1).productCodes());
        assertEquals(BigDecimal.valueOf(200), responses.get(1).totalValue());
        assertEquals("Avenida Teste, 456", responses.get(1).deliveryAddress());
        assertEquals("CREATED", responses.get(1).status());
        assertEquals(secondCreatedAt, responses.get(1).createdAt());

        verify(orderRepository).findAll();
    }
}
