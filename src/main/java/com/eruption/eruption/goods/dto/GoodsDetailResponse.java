package com.eruption.eruption.goods.dto;

import com.eruption.eruption.goods.entity.Goods;
import com.eruption.eruption.goods.entity.GoodsOption;

import java.time.LocalDateTime;
import java.util.List;

public record GoodsDetailResponse(Long id, String title, String description,
                                  LocalDateTime reservationStartAt, LocalDateTime reservationEndAt,
                                  List<GoodsOptionResponse> options) {

    public static GoodsDetailResponse of(Goods goods, List<GoodsOption> options) {
        List<GoodsOptionResponse> optionResponses = options.stream()
                .map(GoodsOptionResponse::from).toList();

        return new GoodsDetailResponse(goods.getId(),
                goods.getTitle(),
                goods.getDescription(),
                goods.getReservationStartAt(),
                goods.getReservationEndAt(),
                optionResponses);
    }
}
