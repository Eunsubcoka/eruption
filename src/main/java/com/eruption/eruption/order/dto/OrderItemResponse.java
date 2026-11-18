package com.eruption.eruption.order.dto;

import com.eruption.eruption.order.entity.OrderItem;

public record OrderItemResponse(Long goodsId, String goodsTitle, Long optionId,
                                String optionName, int qty, int price, int lineAmt) {

    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(item.getGoodsOption().getGoods().getId(), item.getGoodsOption().getGoods().getTitle(),
                item.getGoodsOption().getId(), item.getGoodsOption().getOptionName(), item.getQty(), item.getPrice(),
                item.getPrice() * item.getQty());
    }
}
