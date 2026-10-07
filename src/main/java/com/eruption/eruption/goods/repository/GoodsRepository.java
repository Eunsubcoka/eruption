package com.eruption.eruption.goods.repository;

import com.eruption.eruption.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
