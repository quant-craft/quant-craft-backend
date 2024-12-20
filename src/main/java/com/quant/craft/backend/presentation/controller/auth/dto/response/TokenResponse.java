package com.quant.craft.backend.presentation.controller.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

  private String accessToken;
  private String refreshToken;
}
