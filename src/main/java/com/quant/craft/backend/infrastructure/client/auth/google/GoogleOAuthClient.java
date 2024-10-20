package com.quant.craft.backend.infrastructure.client.auth.google;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.auth.OAuthClient;
import com.quant.craft.backend.infrastructure.client.auth.dto.UserResponse;
import com.quant.craft.backend.infrastructure.client.auth.google.dto.GoogleTokenResponse;
import com.quant.craft.backend.infrastructure.client.auth.google.dto.GoogleUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class GoogleOAuthClient extends OAuthClient {

  public GoogleOAuthClient(
      RestClient client,
      @Value("${application.host.backend}") String host,
      @Value("${oauth.google.client-id}") String clientId,
      @Value("${oauth.google.client-secret}") String clientSecret,
      @Value("${oauth.google.auth-server-url}") String authServerUrl,
      @Value("${oauth.google.api-server-url}") String apiServerUrl
  ) {
    super(client, host, clientId, clientSecret, authServerUrl, apiServerUrl);
  }

  @Override
  public String generateAccessToken(String authorizationCode, String redirectUri) {
    try {
      String url = String.format("%s/token", authServerUrl);
      GoogleTokenResponse response = client.post()
          .uri(url)
          .body(buildAccessTokenRequest(authorizationCode, redirectUri))
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

  @Override
  public UserResponse getUserResponse(String accessToken) {
    try {
      String url = String.format("%s/oauth2/v2/userinfo", apiServerUrl);
      GoogleUserResponse response = client.get()
          .uri(url)
          .header(HttpHeaders.AUTHORIZATION, OAuthClient.BEARER_PREFIX + accessToken)
          .retrieve()
          .body(GoogleUserResponse.class);

      if (response == null) {
        throw new BadRequestException("GoogleUserResponse cannot be null!");
      }

      return UserResponse.builder()
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
