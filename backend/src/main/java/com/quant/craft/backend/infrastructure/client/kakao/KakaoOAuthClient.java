package com.quant.craft.backend.infrastructure.client.kakao;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.dto.UserDTO;
import com.quant.craft.backend.infrastructure.client.kakao.dto.KakaoUserResponse;
import com.quant.craft.backend.infrastructure.client.kakao.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class KakaoOAuthClient {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.auth-server-url}")
    private String authServerUrl;

    @Value("${oauth.kakao.api-server-url}")
    private String apiServerUrl;

    private final RestClient client;

    public String getOAuthLoginUrl(String redirectUrl) {
        return String.format(
                "%s/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                authServerUrl, clientId, redirectUrl
        );
    }

    public String generateAccessToken(String authorizationCode) {
        try {
            String url = String.format("%s/oauth/token", authServerUrl);
            KakaoTokenResponse response = client.post()
                    .uri(url)
                    .body(buildAccessTokenRequest(authorizationCode))
                    .retrieve()
                    .body(KakaoTokenResponse.class);

            if (response == null) {
                throw new BadRequestException("TokenResponse cannot be null!");
            }

            return response.getAccessToken();
        } catch (Exception e) {
            throw new BadRequestException("Access Token Error. e: " + e);
        }
    }

    private MultiValueMap<String, String> buildAccessTokenRequest(String authorizationCode) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);

        return requestBody;
    }

    public UserDTO getUserInformation(String accessToken) {
        try {
            String url = String.format("%s/user/me", apiServerUrl);
            KakaoUserResponse response = client.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name())
                    .retrieve()
                    .body(KakaoUserResponse.class);

            if (response == null) {
                throw new BadRequestException("KakaoUserResponse cannot be null!");
            }

            return UserDTO.builder()
                    .nickname(response.getKakaoAccount().getProfile().getNickname())
                    .oauthId(response.getId())
                    .oauthProvider(OAuthProvider.KAKAO)
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("User Information Error. e: " + e);
        }
    }
}
