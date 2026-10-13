package com.eruption.eruption.goods.dto;


import com.eruption.eruption.goods.entity.GoodsOption;

public record GoodsOptionResponse(Long id, String optionName, int price, int stockQty, int maxQty) {
    public static GoodsOptionResponse from(GoodsOption option) {
        return new GoodsOptionResponse(option.getId(), option.getOptionName(), option.getPrice(), option.getStockQty(), option.getMaxQty());
    }
}
