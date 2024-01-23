package org.example.springbootdeveloper.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.config.jwt.TokenProvider;
import org.example.springbootdeveloper.domain.RefreshToken;
import org.example.springbootdeveloper.domain.User;
import org.example.springbootdeveloper.repository.RefreshTokenRepository;
import org.example.springbootdeveloper.repository.UserRepository;
import org.example.springbootdeveloper.service.UserService;
import org.example.springbootdeveloper.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

// 리프레시 토큰 생성 및 쿠키에 저장, 액세스 토큰을 생성하고 url에 쿼리 파라미터로 붙여서 리다이렉트
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 회원 찾기 또는 새 회원 가입
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"))
                .orElseGet(() -> userService.save(User.builder() // orElseGet : optional이 비어있을 때 get
                        .email(email)
                        .nickname(name)
                        .build()));

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request); // 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터 제거
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response); // OAuth 인증을 위해 저장된 정보도 삭제
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}
