package com.woo.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.woo.boardback.dto.request.user.PatchNicknameRequestDto;
import com.woo.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.woo.boardback.dto.response.ResponseDto;
import com.woo.boardback.dto.response.user.GetSignInUserResponseDto;
import com.woo.boardback.dto.response.user.GetUserResponseDto;
import com.woo.boardback.dto.response.user.PatchNicknameResponseDto;
import com.woo.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.woo.boardback.entity.UserEntity;
import com.woo.boardback.repository.UserRepository;
import com.woo.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null; //0woo 수정
        
        try {
            userEntity = userRepository.findByEmail(email);
            
            if (userEntity == null) {
                return GetSignInUserResponseDto.noExistUser();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email, String token) {
        UserEntity userEntity = null; //0woo 수정
        
        try {
            userEntity = userRepository.findByEmail(email);
            
            if (userEntity == null) {
                return GetSignInUserResponseDto.noExistUser();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return GetSignInUserResponseDto.success(userEntity, token);
    }

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                return GetUserResponseDto.noExistUser();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                return PatchNicknameResponseDto.noExistUser();
            }

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileimage(PatchProfileImageRequestDto dto,
            String email) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                return PatchProfileImageResponseDto.noExistUser();
            }

            String profileImage = dto.getProfileImage();
            
            userEntity.setProfileImage(profileImage);
            System.out.println(userEntity.getProfileImage());
            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }
}
