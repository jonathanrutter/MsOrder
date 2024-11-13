package com.wuppski.microservices.ms_order.repository;

import com.wuppski.microservices.ms_order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
