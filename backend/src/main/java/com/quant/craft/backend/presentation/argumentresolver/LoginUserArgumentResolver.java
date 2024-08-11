package com.quant.craft.backend.presentation.argumentresolver;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.exception.UnauthorizedException;
import com.quant.craft.backend.presentation.interceptor.AuthorizationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequiredLogin.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = AuthorizationExtractor.extract(request);

        if (StringUtils.hasText(accessToken)) {
            Optional<User> user = authService.findUserByAccessToken(accessToken);
            return user.orElseThrow(() -> new NotFoundException("User not found. access token: " + accessToken));
        }
        throw new UnauthorizedException("Invalid Access Token. access token: " + accessToken);
    }

}