package com.eruption.eruption.order.entity;

import com.eruption.eruption.global.common.BaseTimeEntity;
import com.eruption.eruption.goods.entity.GoodsOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private int price;


    @Builder
    public OrderItem(GoodsOption goodsOption, Orders orders, int qty, int price) {
        this.goodsOption = goodsOption;
        this.orders = orders;
        this.qty = qty;
        this.price = price;
    }
}