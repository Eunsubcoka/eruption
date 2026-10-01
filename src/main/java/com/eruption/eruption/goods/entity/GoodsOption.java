package com.eruption.eruption.goods.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsOption {

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
}