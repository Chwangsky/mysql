package com.woo.boardback.entity;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // Lombok annotation for getters, setters, equals, hashCode and toString methods
public class CustomOAuth2User implements OAuth2User {

    private String email;
    private String nickname;
    private String profileImage;

    // Constructor including new fields
    public CustomOAuth2User(String email, String nickname, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return email; // Assuming the email as the unique identifier for the user
    }
    
    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
