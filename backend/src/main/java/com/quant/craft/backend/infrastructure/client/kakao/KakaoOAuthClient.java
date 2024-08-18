package com.quant.craft.backend.infrastructure.client.kakao;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.OAuthClient;
import com.quant.craft.backend.infrastructure.client.dto.UserResponse;
import com.quant.craft.backend.infrastructure.client.kakao.dto.KakaoUserResponse;
import com.quant.craft.backend.infrastructure.client.kakao.dto.KakaoTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOAuthClient extends OAuthClient {

    public KakaoOAuthClient(
            RestClient client,
            @Value("${application.host.backend}") String host,
            @Value("${oauth.kakao.client-id}") String clientId,
            @Value("${oauth.kakao.auth-server-url}") String authServerUrl,
            @Value("${oauth.kakao.api-server-url}") String apiServerUrl
    ) {
        super(client, host, clientId, null, authServerUrl, apiServerUrl);
    }

    @Override
    public String getOAuthLoginUrl() {
        return String.format(
                "%s/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                authServerUrl, clientId, buildRedirectUrl()
        );
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String generateAccessToken(String authorizationCode) {
        try {
            String url = String.format("%s/oauth/token", authServerUrl);
            KakaoTokenResponse response = client.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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

    @Override
    public UserResponse getUserResponse(String accessToken) {
        try {
            String url = String.format("%s/user/me", apiServerUrl);
            KakaoUserResponse response = client.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, OAuthClient.BEARER_PREFIX + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .retrieve()
                    .body(KakaoUserResponse.class);

            if (response == null) {
                throw new BadRequestException("KakaoUserResponse cannot be null!");
            }

            return UserResponse.builder()
                    .nickname(response.getKakaoAccount().getProfile().getNickname())
                    .oauthId(response.getId())
                    .oauthProvider(OAuthProvider.KAKAO)
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("User Information Error. e: " + e);
        }
    }
}
