package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.auth.JwtTokenProvider;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.infrastructure.client.KakaoOAuthClient;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuthClient kakaoClient;

    public String getOAuthLoginUrl(String provider, String redirectUrl) {
        return switch (OAuthProvider.from(provider)) {
            case KAKAO -> {
                yield kakaoClient.getOAuthLoginUrl(redirectUrl);
            }
        };
    }

    @Transactional
    public TokenResponse oauthLogin(String provider, String authorizationCode) {
        return switch (OAuthProvider.from(provider)) {
            case KAKAO -> {
                User userResponse = kakaoClient.buildUser(
                        kakaoClient.generateAccessToken(authorizationCode)
                );

                User user = userRepository.findByOauthId(userResponse.getOauthId()).orElseGet(
                        () -> userRepository.save(userResponse)
                );

                String accessToken = jwtTokenProvider.createAccessToken(user);
                String refreshToken = jwtTokenProvider.createRefreshToken(user);
                user.updateRefreshToken(refreshToken);

                yield new TokenResponse(accessToken, refreshToken);
            }
        };
    }
}