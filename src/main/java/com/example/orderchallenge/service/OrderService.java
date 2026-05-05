package com.example.orderchallenge.service;

import com.example.orderchallenge.dto.CreateOrderRequest;
import com.example.orderchallenge.dto.OrderResponse;
import com.example.orderchallenge.entity.Order;
import com.example.orderchallenge.entity.OrderItem;
import com.example.orderchallenge.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse create(CreateOrderRequest request) {
        Order order = Order.builder()
                .customerCode(request.customerCode())
                .totalValue(request.totalValue())
                .deliveryAddress(request.deliveryAddress())
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .build();

        request.items().forEach(itemRequest -> {
            OrderItem item = OrderItem.builder()
                    .productCode(itemRequest.productCode())
                    .build();

            order.addItem(item);
        });

        Order savedOrder = orderRepository.save(order);

        return toResponse(savedOrder);
    }

    private OrderResponse toResponse(Order order) {
        List<String> productCodes = order.getItems()
                .stream()
                .map(OrderItem::getProductCode)
                .toList();

        return new OrderResponse(
                order.getCustomerCode(),
                productCodes,
                order.getTotalValue(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
