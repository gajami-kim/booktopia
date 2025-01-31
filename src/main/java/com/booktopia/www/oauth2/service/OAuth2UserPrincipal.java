package com.booktopia.www.oauth2.service;

import com.booktopia.www.domain.AuthVO;
import com.booktopia.www.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class OAuth2UserPrincipal implements OAuth2User, UserDetails {

    private UserVO userVO;
    private Map<String, Object> attributes;

    // 일반 로그인용 생성자
    public OAuth2UserPrincipal(UserVO userVO) {
        this.userVO = userVO;
    }

    // 소셜 로그인용 생성자
    public OAuth2UserPrincipal(UserVO userVO, Map<String, Object> attributes) {
        this.userVO = userVO;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return userVO.getPwd();
    }

    @Override
    public String getUsername() {
        return userVO.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return userVO.getName();
    }

    public String getId() {
        return userVO.getId();
    }

    public String getAddress() {
        return userVO.getAddress();
    }

    public String getPhone() {
        return userVO.getPhone();
    }

    public String getAccessToken(){
        return userVO.getAccessToken();
    }
}
