package com.quant.craft.backend.presentation.interceptor;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthorizationExtractor.extract(request);

        if (StringUtils.hasText(accessToken) && authService.findUserByAccessToken(accessToken).isPresent()) {
            return true;
        }

        throw new UnauthorizedException("Invalid Access Token. access token: " + accessToken);
    }
}
