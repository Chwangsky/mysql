package com.woo.boardback.service.implement;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woo.boardback.entity.CustomOAuth2User;
import com.woo.boardback.entity.UserEntity;
import com.woo.boardback.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {


        OAuth2User oAuth2User = super.loadUser(request);
        String provider = request.getClientRegistration().getClientName();

        // for debugging
        
        // try {
        //     System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        UserEntity userEntity = null;
        String userEmail = null;
        String userNickname = null;
        String userProfileImage = null;

        if (provider.equals("Google")) {
            userEmail = "[google]" + oAuth2User.getAttributes().get("email");

            userEntity = userRepository.findByEmail(userEmail);
            if(userEntity == null) {
                userNickname = (String) oAuth2User.getAttributes().get("given_name");
                userNickname = getAlternativeUserNickname(userNickname);

                userProfileImage = (String) oAuth2User.getAttributes().get("picture");
                userEntity = new UserEntity(userEmail, userNickname, userProfileImage, provider);
            }
            

        } else if (provider.equals("Kakao")) {
            Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
            userNickname = (String) properties.get("nickname");

            userEmail = "[kakao]" + userNickname + "@kakao.com"; //! kakao의 경우, AUTH 서버에서 메일을 받아올 수 없어서 일단 이렇게 처리

            userEntity = userRepository.findByEmail(userEmail);

            if (userEntity == null) {
                userNickname = getAlternativeUserNickname(userNickname);

                userProfileImage = (String) properties.get("profile_image");
                userEntity = new UserEntity(userEmail, userNickname, userProfileImage, provider);

            }
        }
        else if (provider.equals("Naver")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");

            userEmail = (String) response.get("email");

            userEntity = userRepository.findByEmail(userEmail);

            if (userEntity == null) {
                userNickname = (String) response.get("nickname");
                userNickname = getAlternativeUserNickname(userNickname);

                userProfileImage = (String) response.get("profile_image");
            
                userEntity = new UserEntity(userEmail, userNickname, userProfileImage, provider);

            }

        }

        if (userEntity != null) {
            userRepository.save(userEntity);
        }
        
        return new CustomOAuth2User(userEmail, userNickname, userProfileImage, provider);
    }


    /**
     * 
     * Auth server에서 받아온 유저 닉네임이 DB에 존재하는지 찾아본다.
     * 만약 동일한 유저 닉네임이 이미 존재한다면, {닉네임}1, {닉네임}2.. 이런 식으로 반환한다.
     * 
     * @param userNickname
     * @return alternative userNickname
     */
    private String getAlternativeUserNickname(String userNickname) {

        Optional<UserEntity> users = userRepository.findByNickname(userNickname);
        
        int tmpNumber = 0;
        while (users.isPresent()) {
            users = userRepository.findByNickname(userNickname + String.valueOf(++tmpNumber));
        }
        userNickname = tmpNumber == 0? userNickname : userNickname + String.valueOf(tmpNumber);
        return userNickname;
    }
}
