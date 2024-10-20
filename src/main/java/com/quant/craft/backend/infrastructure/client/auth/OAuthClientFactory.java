package com.quant.craft.backend.infrastructure.client.auth;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.infrastructure.client.auth.google.GoogleOAuthClient;
import com.quant.craft.backend.infrastructure.client.auth.kakao.KakaoOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuthClientFactory {

  private final KakaoOAuthClient kakaoClient;
  private final GoogleOAuthClient googleClient;

  public OAuthClient get(String provider) {
    return switch (OAuthProvider.from(provider)) {
      case KAKAO -> kakaoClient;
      case GOOGLE -> googleClient;
    };
  }
}
