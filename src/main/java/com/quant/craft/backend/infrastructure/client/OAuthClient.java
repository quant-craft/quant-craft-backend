package com.quant.craft.backend.infrastructure.client;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.infrastructure.client.dto.UserResponse;
import com.quant.craft.backend.presentation.controller.AuthController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public abstract class OAuthClient {

    protected static final String BEARER_PREFIX = "Bearer ";

    protected final RestClient client;

    protected final String host;

    protected final String clientId;

    protected final String clientSecret;

    protected final String authServerUrl;

    protected final String apiServerUrl;

    public abstract OAuthProvider getOAuthProvider();
    public abstract String getOAuthLoginUrl();

    public abstract String generateAccessToken(String authorizationCode);

    public abstract UserResponse getUserResponse(String accessToken);

    protected String buildRedirectUrl() {
        return String.format(AuthController.REDIRECT_URL_FORMAT, host, getOAuthProvider().name().toLowerCase());
    }
}
