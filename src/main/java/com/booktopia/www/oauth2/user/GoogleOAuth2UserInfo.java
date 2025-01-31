package com.booktopia.www.oauth2.user;

import java.util.Map;



public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String pwd;
    private final String name;
    private final String email;

    public GoogleOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        this.attributes = attributes;
        this.id = (String) attributes.get("sub"); // 사용자 이메일
        this.name = (String) attributes.get("name"); // 사용자 이름
        this.pwd = (String) attributes.get("sub");// 사용자 id 토큰
        this.email = (String) attributes.get("email");
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public String getUserType() {
        return "google";
    }

}