package com.eruption.eruption.global.exception;


import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {

        log.warn(e.getMessage(),e.getErrorCode());

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.fail(e.getErrorCode()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        log.warn(e.getMessage(),ErrorCode.INVALID_INPUT.getCode());

        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorCode.INVALID_INPUT));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> serverError(Exception e) {

        log.error(e.getMessage(),ErrorCode.INTERNAL_SERVER_ERROR.getCode());

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        log.warn(e.getMessage(),ErrorCode.INVALID_INPUT.getCode());

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST_BODY.getStatus())
                .body(ApiResponse.fail(ErrorCode.INVALID_REQUEST_BODY));
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtException(JwtException e) {

        log.warn(e.getMessage(),ErrorCode.INVALID_REFRESH_TOKEN.getCode());

        return ResponseEntity.status(ErrorCode.INVALID_REFRESH_TOKEN.getStatus())
                .body(ApiResponse.fail(ErrorCode.INVALID_REFRESH_TOKEN));
    }

}

