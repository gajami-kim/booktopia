package com.booktopia.www.oauth2.handler;

import com.booktopia.www.domain.UserVO;
import com.booktopia.www.jwt.TokenProvider;
import com.booktopia.www.oauth2.utill.CookieUtils;
import com.booktopia.www.oauth2.utill.HttpCookieOAuth2AuthorizationRequestRepository;
import com.booktopia.www.oauth2.service.OAuth2UserPrincipal;
import com.booktopia.www.oauth2.user.OAuth2Provider;
import com.booktopia.www.oauth2.user.OAuth2UserUnlinkManager;
import com.booktopia.www.repository.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.booktopia.www.oauth2.utill.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.booktopia.www.oauth2.utill.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final TokenProvider tokenProvider;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl =determineTargetUrl(request, response, authentication);;

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되었습니다. 다음으로 리디렉션할 수 없습니다." + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("fail");
        log.info("mode >> {}" , mode);

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "principal null")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {
            // TODO: 액세스 토큰, 리프레시 토큰 발급
            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken();

            // TODO: 리프레시 토큰 DB 저장
            log.info("id={}, name={},pwd={} accessToken={}",
                    principal.getUserVO().getId(),
                    principal.getUserVO().getName(),
                    principal.getUserVO().getPwd(),
                    principal.getUserVO().getAccessToken()
            );
                log.info("accessToken Handler>> {}", accessToken);
                log.info("refreshToken Handler>> {}", refreshToken);


                return UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("access_token", accessToken)
                        .queryParam("refresh_token", refreshToken)
                        .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {
            String ut = principal.getUserVO().getUserType();
            log.info("ut >> {}",ut);
            String accessToken = principal.getUserVO().getAccessToken();
            OAuth2Provider provider = OAuth2Provider.valueOf(ut.toUpperCase());
            String id = principal.getUserVO().getId();

            log.info("pro > {} ",provider);
            log.info("access >> {}",accessToken);

            // TODO: DB 삭제
            userMapper.deleteMyPageUser(id);
            // TODO: 리프레시 토큰 삭제

            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .path("/user/logout")
                    .queryParam("unlink", "success")
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("login", "failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
