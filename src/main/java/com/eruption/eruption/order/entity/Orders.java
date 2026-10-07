package com.eruption.eruption.order.entity;

import com.eruption.eruption.global.common.BaseTimeEntity;
import com.eruption.eruption.order.enums.OrderStatus;
import com.eruption.eruption.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false,unique = true)
    private String orderNo;

    @Column(nullable = false)
    private int totalAmt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime expiredAt;




    @Builder
    public Orders(User user, String orderNo, int totalAmt,OrderStatus status,
                  LocalDateTime expiredAt){
        this.user = user;
        this.orderNo = orderNo;
        this.status = OrderStatus.PENDING_PAYMENT;
        this.totalAmt = totalAmt;
        this.expiredAt = expiredAt;
    }
}
