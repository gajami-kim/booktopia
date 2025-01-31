package com.booktopia.www.oauth2.user;

import com.booktopia.www.domain.UserVO;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String pwd;
    private final String name;
    private final String email;
    private final UserVO userVO = new UserVO();

    public NaverOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        // attributes 맵의 response 키의 값에 실제 attributes 맵이 할당되어 있음
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.email = (String) this.attributes.get("email");
        this.pwd = (String) this.attributes.get("id");
        this.id = (String) this.attributes.get("id");
        this.name = (String) this.attributes.get("name");

    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.NAVER;
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
    public String getPwd() {return pwd;}

    @Override
    public String getUserType() {
        return "naver";
    }

}