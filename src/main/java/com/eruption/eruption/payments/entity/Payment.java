package com.eruption.eruption.payments.entity;


import com.eruption.eruption.global.common.BaseTimeEntity;
import com.eruption.eruption.order.entity.Orders;
import com.eruption.eruption.payments.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_id",unique=true)
    private Orders orders;

    @Column(nullable = true)
    private String paymentKey;

    @Column(nullable = false)
    private int amt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = true)
    private LocalDateTime approvedAt;




    @Builder
    public Payment(Orders orders, String paymentKey, int amt){
        this.orders = orders;
        this.paymentKey = paymentKey;
        this.amt = amt;
        this.status = PaymentStatus.READY;
    }
}
