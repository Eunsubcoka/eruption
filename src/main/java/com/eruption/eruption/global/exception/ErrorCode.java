package com.eruption.eruption.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;



@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"DUPLICATE_EMAIL","이미 가입한 이메일입니다."),
    GOODS_NOT_FOUND(HttpStatus.NOT_FOUND,"GOODS_NOT_FOUND","존재하지 않는 상품입니다."),
    OUT_OF_STOCK(HttpStatus.CONFLICT,"OUT_OF_STOCK","재고가 부족합니다"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED,"INVALID_CREDENTIALS","이메일이나 비밀번호가 일치하지 않습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "입력값이 올바르지 않습니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_BODY", "요청 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
