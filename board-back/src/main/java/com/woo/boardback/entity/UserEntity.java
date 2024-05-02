package com.woo.boardback.entity;

import java.util.UUID;

import com.woo.boardback.dto.request.auth.SignUpRequestDto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
public class UserEntity {

    @Id
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @Nullable
    @Column(unique = true)
    private String telNumber;

    @Nullable
    private String address;

    @Nullable
    private String addressDetail;

    @Nullable
    private String profileImage;

    @Nullable
    private boolean agreedPersonal;

    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
        this.addressDetail = dto.getAddressDetail();
        this.agreedPersonal = dto.getAgreedPersonal();
    }

    /*
     * this constructor is for OAuth2.0 Authentication
     */
    public UserEntity(String email, String nickname, String profileImage, String provider) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.password = UUID.randomUUID().toString(); // password doesn't mean nothing in OAuth2.0
        this.address = "";
        this.addressDetail = "";
        this.agreedPersonal = true;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    
}
