package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

        String url = authService.getOAuthLoginUrl(redirectUrl);
        return new RedirectView(url);
    }

    @GetMapping("/oauth/kakao")
    public String redirectKaKao(@RequestParam("code") String code) {
        System.out.println("Authorization code: " + code);
        return "redirect:/";
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
}
