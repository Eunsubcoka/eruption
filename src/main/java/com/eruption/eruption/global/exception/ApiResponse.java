package com.eruption.eruption.global.exception;

public record ApiResponse<T>(boolean success, T data, ErrorBody error) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<Void> fail(ErrorCode code) {
        return new ApiResponse<>(false, null, new ErrorBody(code.getCode(), code.getMessage()));
    }


    public record ErrorBody(String code, String message) {}
}
