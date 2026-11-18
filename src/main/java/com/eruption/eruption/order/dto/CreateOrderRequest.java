package com.eruption.eruption.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(@Valid @NotEmpty List<OrderItemRequest> items) {
    public record OrderItemRequest(@NotNull Long optionId, @Min(1) int qty) {

    }
}
