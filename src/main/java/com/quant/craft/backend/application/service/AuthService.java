package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.auth.JwtTokenProvider;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.client.auth.OAuthClient;
import com.quant.craft.backend.infrastructure.client.auth.OAuthClientFactory;
import com.quant.craft.backend.infrastructure.client.auth.dto.UserResponse;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.presentation.controller.auth.dto.request.AuthorizationCodeRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.response.TokenResponse;
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

    @Transactional
    public TokenResponse oauthLogin(String provider, AuthorizationCodeRequest request) {
        OAuthClient oAuthClient = oAuthClientFactory.get(provider);

        UserResponse userResponse = oAuthClient.getUserResponse(
                oAuthClient.generateAccessToken(request.getCode(), request.getRedirectUri())
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