package com.eruption.eruption.goods.service;

import com.eruption.eruption.global.exception.GoodsNotFoundException;
import com.eruption.eruption.goods.dto.GoodsDetailResponse;
import com.eruption.eruption.goods.dto.GoodsListResponse;
import com.eruption.eruption.goods.entity.Goods;
import com.eruption.eruption.goods.entity.GoodsOption;
import com.eruption.eruption.goods.repository.GoodsOptionRepository;
import com.eruption.eruption.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsOptionRepository goodsOptionRepository;

    // 굿즈 리스트 조회
    @Transactional(readOnly = true)
    public List<GoodsListResponse> getGoodsList() {
        return goodsRepository.findAll().stream()
                .map(GoodsListResponse::from)
                .toList();
    }

    // 굿즈 디테일 조회(굿즈와 굿즈 옵션을 담아서)
    @Transactional(readOnly = true)
    public GoodsDetailResponse getGoodsDetail(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(GoodsNotFoundException::new);
        List<GoodsOption> options = goodsOptionRepository.findByGoodsId(goodsId);

        return GoodsDetailResponse.of(goods,options);
    }
}
