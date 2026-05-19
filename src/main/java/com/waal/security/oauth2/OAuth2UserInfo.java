package com.waal.security.oauth2;

import com.waal.domain.User;

public interface OAuth2UserInfo {
    String getProviderId();
    String getEmail();
    String getNickname();
    String getProfileImageUrl();
    User.OAuthProvider getProvider();
}
