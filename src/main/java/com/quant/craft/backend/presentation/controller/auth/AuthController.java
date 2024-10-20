package com.quant.craft.backend.presentation.controller.auth;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.auth.dto.request.AuthorizationCodeRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.request.RefreshTokenRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    TokenResponse tokenResponse = authService.refreshAccessTokenWithRefreshToken(
        request.getRefreshToken());
    return ResponseEntity.ok(tokenResponse);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<Void> logout(@RequiredLogin User user) {
    authService.logout(user);
    return ResponseEntity.noContent().build();
  }
}
