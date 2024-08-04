package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.OAuthProvider;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.infrastructure.client.KakaoOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final KakaoOAuthClient kakaoClient;

    public String getOAuthLoginUrl(String provider, String redirectUrl) {
        return switch (OAuthProvider.from(provider)) {
            case KAKAO -> {
                yield kakaoClient.getOAuthLoginUrl(redirectUrl);
            }
        };
    }

    public User oauthLogin(String provider, String authorizationCode) {
        return switch (OAuthProvider.from(provider)) {
            case KAKAO -> {
                String accessToken = kakaoClient.requestAccessToken(authorizationCode);
                User user = kakaoClient.requestUserInformation(accessToken);
                yield user;
            }
        };
    }
}