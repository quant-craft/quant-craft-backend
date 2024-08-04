package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.presentation.dto.RefreshTokenRequest;
import com.quant.craft.backend.presentation.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth/authorize")
    public RedirectView getAuthorizationCode(
            HttpServletRequest request,
            @RequestParam("provider") String provider
    ) {
        String redirectUrl = String.format("%s://%s:%d/login/oauth/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                provider
        );

        String url = authService.getOAuthLoginUrl(provider, redirectUrl);
        return new RedirectView(url);
    }

    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<TokenResponse> login(
            @PathVariable("provider") String provider,
            @RequestParam("code") String code
    ) {
        return ResponseEntity.ok(authService.oauthLogin(provider, code));
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = authService.refreshAccessTokenWithRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
}
