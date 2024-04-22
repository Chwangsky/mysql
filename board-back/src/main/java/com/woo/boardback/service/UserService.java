package com.woo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.woo.boardback.dto.request.user.PatchNicknameRequestDto;
import com.woo.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.woo.boardback.dto.response.user.GetSignInUserResponseDto;
import com.woo.boardback.dto.response.user.GetUserResponseDto;
import com.woo.boardback.dto.response.user.PatchNicknameResponseDto;
import com.woo.boardback.dto.response.user.PatchProfileImageResponseDto;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email, String token);

    ResponseEntity<? super GetUserResponseDto> getUser(String email);
    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email);
    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileimage(PatchProfileImageRequestDto dto, String email);
    
}
