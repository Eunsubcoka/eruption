package com.eruption.eruption.order.controller;

import com.eruption.eruption.global.exception.ApiResponse;
import com.eruption.eruption.order.dto.CreateOrderRequest;
import com.eruption.eruption.order.dto.OrderResponse;
import com.eruption.eruption.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@AuthenticationPrincipal Long userId,@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(orderService.createOrder(userId,createOrderRequest)));
    }
}
