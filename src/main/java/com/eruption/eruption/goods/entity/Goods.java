package com.eruption.eruption.goods.entity;

import com.eruption.eruption.global.common.BaseTimeEntity;
import com.eruption.eruption.global.exception.BusinessException;
import com.eruption.eruption.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime reservationStartAt;

    @Column(nullable = false)
    private LocalDateTime reservationEndAt;

        public void validateReservable(LocalDateTime now) {
            if(now.isAfter(reservationEndAt))
                throw new BusinessException(ErrorCode.RESERVATION_CLOSED);

            if(now.isBefore(reservationStartAt))
                throw new BusinessException(ErrorCode.RESERVATION_NOT_OPEN);
        }

    @Builder
    public Goods(String title, String description,
                 LocalDateTime reservationStartAt, LocalDateTime reservationEndAt) {
        this.title = title;
        this.description = description;
        this.reservationStartAt = reservationStartAt;
        this.reservationEndAt = reservationEndAt;
    }
}
