package com.eruption.eruption.goods.controller;

import com.eruption.eruption.global.exception.ApiResponse;
import com.eruption.eruption.goods.dto.GoodsDetailResponse;
import com.eruption.eruption.goods.dto.GoodsListResponse;
import com.eruption.eruption.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    // 굿즈 목록조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GoodsListResponse>>> getGoods() {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(goodsService.getGoodsList()));
    }
    // 굿즈 상세조회
    @GetMapping("/{goodsId}")
    public ResponseEntity<ApiResponse<GoodsDetailResponse>> getGoodsDetail(
            @PathVariable Long goodsId) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(goodsService.getGoodsDetail(goodsId)));
    }


}
