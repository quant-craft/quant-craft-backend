package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.infrastructure.client.KakaoOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final KakaoOAuthClient kakaoClient;

    public String getOAuthLoginUrl(String redirectUrl) {
        return kakaoClient.getOAuthLoginUrl(redirectUrl);
    }

    public User kakaoOauthLogin(String authorizationCode) {
        String accessToken = kakaoClient.requestAccessToken(authorizationCode);
        User user = kakaoClient.requestUserInformation(accessToken);

        return user;
    }
}
