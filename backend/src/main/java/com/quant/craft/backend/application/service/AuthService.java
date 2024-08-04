package com.quant.craft.backend.application.service;

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
}
