package com.eruption.eruption.order.dto;

import com.eruption.eruption.order.entity.OrderItem;
import com.eruption.eruption.order.entity.Orders;
import com.eruption.eruption.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long orderId, String orderNo, OrderStatus status,
                            int totalAmt, LocalDateTime expiredAt, LocalDateTime createdAt,
                            List<OrderItemResponse> items) {

    public static OrderResponse of(Orders orders, List<OrderItem> items) {

        return new OrderResponse(orders.getId(), orders.getOrderNo(), orders.getStatus(), orders.getTotalAmt(),
                orders.getExpiredAt(), orders.getCreatedAt(),
                items.stream().map(OrderItemResponse::from).toList());
    }
}
