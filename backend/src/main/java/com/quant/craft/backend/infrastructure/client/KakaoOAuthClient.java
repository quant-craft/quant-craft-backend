package com.quant.craft.backend.infrastructure.client;

import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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

            String tokenResponse = client.post()
                    .uri(url)
                    .body(buildAccessTokenRequest(authorizationCode))
                    .retrieve()
                    .body(String.class);

            return parseAccessToken(tokenResponse);
        } catch (Exception e) {
            throw new RuntimeException("Access Token Error. e: " + e);
        }
    }

    private MultiValueMap<String, String> buildAccessTokenRequest(String authorizationCode) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);

        return requestBody;
    }

    private String parseAccessToken(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("access_token");
    }

    public User buildUser(String accessToken) {
        try {
            String url = String.format("%s/user/me", apiServerUrl);
            String response = client.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name())
                    .retrieve()
                    .body(String.class);

            return parseUser(response);
        } catch (Exception e) {
            throw new RuntimeException("User Information Error. e: " + e);
        }
    }

    protected User parseUser(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
        JSONObject profile = (JSONObject) kakaoAccount.get("profile");

        User user = User.builder()
                .nickname(profile.getString("nickname"))
                .oauthId(String.valueOf(jsonObject.getLong("id")))
                .oauthProvider(OAuthProvider.KAKAO)
                .build();

        if (kakaoAccount.has("email")) {
            user.updateEmail(kakaoAccount.getString("email"));
        }
        return user;
    }
}
