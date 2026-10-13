package com.eruption.eruption.goods.repository;

import com.eruption.eruption.goods.entity.GoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, Long> {
        List<GoodsOption> findByGoodsId(Long goodsId);
}

