package com.woo.boardback.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woo.boardback.common.ResponseCode;
import com.woo.boardback.common.ResponseMessage;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;  // Jackson 라이브러리를 사용하여 JSON 변환

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그아웃 성공 시 수행할 로직
        if (authentication != null) {
            System.out.println("로그아웃 로직 수행!");
        }

        // JSON 응답 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("code", ResponseCode.SUCCESS);
        responseMap.put("message", ResponseMessage.SUCCESS);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}
