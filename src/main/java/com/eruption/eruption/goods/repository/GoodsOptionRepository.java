package com.eruption.eruption.goods.repository;

import com.eruption.eruption.goods.entity.GoodsOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, Long> {
        List<GoodsOption> findByGoodsId(Long goodsId);
        @Query("select o from GoodsOption o join fetch o.goods where o.id in :ids")
        List<GoodsOption> findAllWithGoodsByIdIn(@Param("ids") List<Long> ids);
}

