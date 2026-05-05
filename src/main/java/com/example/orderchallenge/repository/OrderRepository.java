package com.example.orderchallenge.repository;

import com.example.orderchallenge.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
