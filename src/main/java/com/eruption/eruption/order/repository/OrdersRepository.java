package com.eruption.eruption.order.repository;

import com.eruption.eruption.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
