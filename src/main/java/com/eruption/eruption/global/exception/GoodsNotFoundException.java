package com.eruption.eruption.global.exception;

public class GoodsNotFoundException extends RuntimeException{
    public GoodsNotFoundException() { super("굿즈를 찾을 수 없습니다."); }

}
