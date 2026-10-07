package com.eruption.eruption.goods.dto;

import com.eruption.eruption.goods.entity.Goods;

import java.time.LocalDateTime;

public record GoodsListResponse(Long id, String title,
                                LocalDateTime reservationStartAt,
                                LocalDateTime reservationEndAt) {

    public static GoodsListResponse from(Goods goods) {
        return new GoodsListResponse(goods.getId(), goods.getTitle(),
                goods.getReservationStartAt(), goods.getReservationEndAt());
    }
}

