package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.auth.JwtTokenProvider;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.client.dto.UserDTO;
import com.quant.craft.backend.infrastructure.client.kakao.KakaoOAuthClient;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
                UserDTO userDTO = kakaoClient.getUserInformation(
                        kakaoClient.generateAccessToken(authorizationCode)
                );

                User user = userRepository.findByOauthId(userDTO.getOauthId()).orElseGet(
                        () -> userRepository.save(userDTO.toEntity())
                );

                String accessToken = jwtTokenProvider.createAccessToken(user);
                String refreshToken = jwtTokenProvider.createRefreshToken(user);
                user.updateRefreshToken(refreshToken);

                yield new TokenResponse(accessToken, refreshToken);
            }
        };
    }

    public TokenResponse refreshAccessTokenWithRefreshToken(String refreshToken) {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found User. refreshToken: " + refreshToken);
                });

        return new TokenResponse(
                jwtTokenProvider.createAccessToken(user),
                refreshToken
        );
    }

    public Optional<User> findUserByAccessToken(String accessToken) {
        jwtTokenProvider.validateAccessToken(accessToken);
        String userId = jwtTokenProvider.getAccessTokenPayload(accessToken);

        return userRepository.findById(Long.parseLong(userId));
    }

    @Transactional
    public void logout(User user) {
        user.updateRefreshToken(null);
        userRepository.save(user);
    }
}