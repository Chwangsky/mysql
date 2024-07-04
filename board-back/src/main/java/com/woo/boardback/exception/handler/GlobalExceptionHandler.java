package com.woo.boardback.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.woo.boardback.dto.response.ResponseDto;
import com.woo.boardback.exception.exceptions.FileStorageException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ResponseDto> handleFileStorageException(FileStorageException ex, WebRequest request) {
        log.info("존재하지 않는 이미지 파일입니다.");
        log.info("FileStorageException: ", ex); // 추가해주어야 에러 핸들링이 정상적으로 됨
        return ResponseDto.databaseError();
    }
}