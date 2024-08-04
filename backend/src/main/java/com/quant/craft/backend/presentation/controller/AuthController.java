package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/oauth/authorize")
    public RedirectView getAuthorizationCode(
            HttpServletRequest request,
            @RequestParam("provider") String provider
    ) {
        String redirectUrl = String.format("%s://%s:%d/oauth/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                provider
        );

        String url = authService.getOAuthLoginUrl(provider, redirectUrl);
        return new RedirectView(url);
    }

    @GetMapping("/oauth/{provider}")
    public ResponseEntity<User> login(@PathVariable("provider") String provider, @RequestParam("code") String code) {
        User user = authService.oauthLogin(provider, code);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
}
