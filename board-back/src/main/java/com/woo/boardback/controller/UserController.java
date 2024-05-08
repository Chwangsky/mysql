package com.woo.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.nimbusds.jose.util.StandardCharset;
import com.woo.boardback.dto.request.user.PatchNicknameRequestDto;
import com.woo.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.woo.boardback.dto.response.auth.SignInResponseDto;
import com.woo.boardback.dto.response.user.GetSignInUserResponseDto;
import com.woo.boardback.dto.response.user.GetUserResponseDto;
import com.woo.boardback.dto.response.user.PatchNicknameResponseDto;
import com.woo.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.woo.boardback.entity.UserEntity;
import com.woo.boardback.provider.JwtProvider;
import com.woo.boardback.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser (
        @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
        
        return response;
    }

    // Jwt 토큰 갱신용 //
    // 서비스 로직인데 디버깅용으로 일단 컨트롤러에 넣음 //
    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> handleCookie (
        @AuthenticationPrincipal String email
    ) {
        String token = jwtProvider.create(email);
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email, token);
        return response;
    }

    @GetMapping("/{email}")
    public ResponseEntity<? super GetUserResponseDto> getUser (
        @PathVariable("email") String encodedEmail
    ) {
        String email = UriUtils.decode(encodedEmail, StandardCharset.UTF_8);
        ResponseEntity<? super GetUserResponseDto> response = userService.getUser(email);
        return response;
    }

    @PatchMapping("/nickname")
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(
        @RequestBody @Valid PatchNicknameRequestDto requestBody,
        @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super PatchNicknameResponseDto> response = userService.patchNickname(requestBody, email);
        return response;
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
        @RequestBody @Valid PatchProfileImageRequestDto requestBody,
        @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileimage(requestBody, email);
        return response;
    }
}
