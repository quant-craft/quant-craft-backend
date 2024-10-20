package com.quant.craft.backend.infrastructure.client.auth;

import com.quant.craft.backend.infrastructure.client.auth.dto.UserResponse;
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

  public abstract String generateAccessToken(String authorizationCode, String redirectUri);

  public abstract UserResponse getUserResponse(String accessToken);

}
