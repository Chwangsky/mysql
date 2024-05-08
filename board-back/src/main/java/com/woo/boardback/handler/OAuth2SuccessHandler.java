package com.woo.boardback.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woo.boardback.common.ResponseCode;
import com.woo.boardback.common.ResponseMessage;
import com.woo.boardback.dto.response.ResponseDto;
import com.woo.boardback.dto.response.auth.SignInResponseDto;
import com.woo.boardback.entity.CustomOAuth2User;
import com.woo.boardback.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override                       
	public void onAuthenticationSuccess(
            HttpServletRequest request, 
            HttpServletResponse response,
	        Authentication authentication
        ) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String code = ResponseCode.SUCCESS;
        String message = ResponseMessage.SUCCESS;
        String email = oAuth2User.getName();
        String token = jwtProvider.create(email);
        int expirationTime = 3600;

        // Create a map to hold the response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("code", code);
        responseData.put("message", message);
        responseData.put("token", token);
        responseData.put("expirationTime", expirationTime);

        String jsonResponse = objectMapper.writeValueAsString(responseData);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonResponse);
        response.sendRedirect("http://localhost:3000/oauth-response/" + token + "/3600");

	}
}