package com.booktopia.www.oauth2.service;


import com.booktopia.www.domain.UserVO;
import com.booktopia.www.oauth2.user.*;
import com.booktopia.www.repository.CouponUseMapper;
import com.booktopia.www.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CouponUseMapper couponUseMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo;

        String accessToken = userRequest.getAccessToken().getTokenValue();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleOAuth2UserInfo(accessToken, oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverOAuth2UserInfo(accessToken, oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoOAuth2UserInfo(accessToken, oAuth2User.getAttributes());
        } else {
            log.error("지원되지 않는 로그인 요청: {}", registrationId);
            throw new OAuth2AuthenticationException("지원되지 않는 로그인 요청");
        }

        String providerId = oAuth2UserInfo.getId();
        log.info("providerId >>> {}",providerId);
        String username = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String type = oAuth2UserInfo.getUserType();
        String role = "ROLE_USER";
        log.info("att >> {}",oAuth2User.getAttributes());

        // principalName 검증
        if (providerId == null || providerId.isEmpty()) {
            throw new OAuth2AuthenticationException("principalName cannot be empty");
        }

        UserVO user = userMapper.selectId(providerId);
        UserVO uvo = new UserVO();

        if (user == null) {
            log.info("로그인이 최초 입니다.");
            uvo.setId(providerId);
            uvo.setName(username);
            uvo.setEmail(email);
            uvo.setUserType(type);
            uvo.setAccessToken(accessToken);
            userMapper.joinInsertOauth(uvo);
            couponUseMapper.insertUserCoupon(uvo.getId());
            int isOk = userMapper.countAuth(providerId);
            if(isOk < 0){
                userMapper.insertAuth(providerId);
            }
            log.info("uvo >> {}",uvo);
        } else if(user.getUserDel().equals("Y")){
            try {
                throw new AccountNotFoundException("유저가 탈퇴처리 중입니다.");
            } catch (AccountNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            log.info("이미 로그인 한적이 있습니다.");
            // 유저 정보 업데이트 (필요 시)
            uvo = user;
            uvo.setAccessToken(accessToken);
            userMapper.updateAccessToken(uvo);
        }
        return new OAuth2UserPrincipal(uvo, oAuth2User.getAttributes());
    }
}