package com.quant.craft.backend.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthClient {

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.auth-server-url}")
    private String authServerUrl;

    public String getOAuthLoginUrl(String redirectUrl) {
        return String.format(
                "%s/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                authServerUrl, clientId, redirectUrl
        );
    }
}
