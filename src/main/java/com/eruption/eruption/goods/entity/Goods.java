package com.eruption.eruption.goods.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {

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

    @Builder
    public Goods(String title, String description,
                 LocalDateTime reservationStartAt, LocalDateTime reservationEndAt) {
        this.title = title;
        this.description = description;
        this.reservationStartAt = reservationStartAt;
        this.reservationEndAt = reservationEndAt;
    }
}
