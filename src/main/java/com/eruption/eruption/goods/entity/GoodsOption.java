package com.eruption.eruption.goods.entity;

import com.eruption.eruption.global.common.BaseTimeEntity;
import com.eruption.eruption.global.exception.BusinessException;
import com.eruption.eruption.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void decreaseStock(int qty) {
        if(qty <= 0) throw new BusinessException(ErrorCode.INVALID_INPUT);
        if(qty > maxQty) throw new BusinessException(ErrorCode.EXCEED_MAX_QTY);
        if(stockQty < qty) throw new BusinessException(ErrorCode.OUT_OF_STOCK);
        this.stockQty -= qty;
    }

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