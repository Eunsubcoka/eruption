package com.eruption.eruption.goods.entity;

import com.eruption.eruption.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQty;

    @Column(nullable = false)
    private int maxQty;

    @Builder
    public GoodsOption(Goods goods, String optionName, int price,
                       int stockQty, int maxQty) {
        this.goods = goods;
        this.optionName = optionName;
        this.price = price;
        this.stockQty = stockQty;
        this.maxQty = maxQty;

    }
}