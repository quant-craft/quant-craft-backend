package com.quant.craft.backend.presentation.configuration;

import com.quant.craft.backend.application.service.AuthService;
import com.quant.craft.backend.presentation.argumentresolver.LoginUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

  private final AuthService authService;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserArgumentResolver());
  }

  @Bean
  public LoginUserArgumentResolver loginUserArgumentResolver() {
    return new LoginUserArgumentResolver(authService);
  }
}
