package com.woo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.woo.boardback.dto.request.auth.SignInRequestDto;
import com.woo.boardback.dto.request.auth.SignUpRequestDto;
import com.woo.boardback.dto.response.auth.SignInResponseDto;
import com.woo.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
