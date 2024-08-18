package com.quant.craft.backend.infrastructure.client.google;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.dto.UserDTO;
import com.quant.craft.backend.infrastructure.client.google.dto.GoogleUserResponse;
import com.quant.craft.backend.infrastructure.client.google.dto.GoogleTokenResponse;
import com.quant.craft.backend.presentation.controller.AuthController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class GoogleOAuthClient {

    private static final String GET_AUTHORIZATION_CODE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";

    @Value("${application.host.backend}")
    private String host;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.auth-server-url}")
    private String authServerUrl;

    @Value("${oauth.google.api-server-url}")
    private String apiServerUrl;

    private final RestClient client;

    public String getOAuthLoginUrl() {
        return String.format(
                "%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                GET_AUTHORIZATION_CODE_URL, clientId, buildRedirectUrl(), SCOPE
        );
    }

    private String buildRedirectUrl() {
        return String.format(AuthController.REDIRECT_URL_FORMAT, host, OAuthProvider.GOOGLE.name().toLowerCase());
    }

    public String generateAccessToken(String authorizationCode) {
        try {
            String url = String.format("%s/token", authServerUrl);
            GoogleTokenResponse response = client.post()
                    .uri(url)
                    .body(buildAccessTokenRequest(authorizationCode, buildRedirectUrl()))
                    .retrieve()
                    .body(GoogleTokenResponse.class);

            if (response == null) {
                throw new BadRequestException("TokenResponse cannot be null!");
            }

            return response.getAccessToken();
        } catch (Exception e) {
            throw new BadRequestException("Access Token Error. e: " + e);
        }
    }

    private MultiValueMap<String, String> buildAccessTokenRequest(
            String authorizationCode,
            String redirectUrl
    ) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUrl);

        return requestBody;
    }

    public UserDTO getUserInformation(String accessToken) {
        try {
            String url = String.format("%s/oauth2/v2/userinfo", apiServerUrl);
            GoogleUserResponse response = client.get()
                    .uri(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(GoogleUserResponse.class);

            if (response == null) {
                throw new BadRequestException("GoogleUserResponse cannot be null!");
            }

            return UserDTO.builder()
                    .email(response.getEmail())
                    .nickname(response.getName())
                    .oauthId(response.getId())
                    .oauthProvider(OAuthProvider.GOOGLE)
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("User Information Error. e: " + e);
        }
    }
}
