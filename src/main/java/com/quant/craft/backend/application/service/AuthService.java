package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.auth.JwtTokenProvider;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.client.OAuthClient;
import com.quant.craft.backend.infrastructure.client.OAuthClientFactory;
import com.quant.craft.backend.infrastructure.client.dto.UserResponse;
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
    private final OAuthClientFactory oAuthClientFactory;

    public String getOAuthLoginUrl(String provider) {
        return oAuthClientFactory.get(provider).getOAuthLoginUrl();
    }

    @Transactional
    public TokenResponse oauthLogin(String provider, String authorizationCode) {
        OAuthClient oAuthClient = oAuthClientFactory.get(provider);

        UserResponse userResponse = oAuthClient.getUserResponse(
                oAuthClient.generateAccessToken(authorizationCode)
        );

        User user = userRepository.findByOauthId(userResponse.getOauthId()).orElseGet(
                () -> userRepository.save(userResponse.toEntity())
        );

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        user.updateRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshAccessTokenWithRefreshToken(String refreshToken) {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("Not found User. refreshToken: " + refreshToken));

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