package com.example.orderchallenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.digester.ArrayStack;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerCode;

    private BigDecimal totalValue;

    private String deliveryAddress;

    private String status;

    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> items = new ArrayStack<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
