package com.waal.security.oauth2;

import com.waal.domain.User;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private final String providerId;
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> profile;

    @SuppressWarnings("unchecked")
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.providerId = String.valueOf(attributes.get("id"));
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = kakaoAccount != null
                ? (Map<String, Object>) kakaoAccount.get("profile")
                : Map.of();
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
    }

    @Override
    public String getNickname() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getProfileImageUrl() {
        return (String) profile.get("profile_image_url");
    }

    @Override
    public User.OAuthProvider getProvider() {
        return User.OAuthProvider.KAKAO;
    }
}
