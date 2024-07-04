package com.woo.boardback.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.woo.boardback.dto.response.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler {

    // MethodArgumentNotValidException.class : 객체의 바인딩 중 유효성 문제가 발생한 경우
    // HttpMessageNotReadableException.class : HTTP 요청 본문을 파싱할 수 없거나 잘못된 형식의 데이터가
    // 들어온 경우
    @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception e) {
        log.info("바인딩 예외가 발생했습니다. \n", e);

        return ResponseDto.validationFailed();
    }

}
