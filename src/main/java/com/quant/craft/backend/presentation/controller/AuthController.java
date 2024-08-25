package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.dto.AuthorizationCodeRequest;
import com.quant.craft.backend.presentation.dto.RefreshTokenRequest;
import com.quant.craft.backend.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/oauth/{provider}")
    public ResponseEntity<TokenResponse> login(
            @PathVariable("provider") String provider,
            @RequestBody AuthorizationCodeRequest request
    ) {
        return ResponseEntity.ok(authService.oauthLogin(provider, request));
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = authService.refreshAccessTokenWithRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequiredLogin User user) {
        authService.logout(user);
        return ResponseEntity.noContent().build();
    }
}
