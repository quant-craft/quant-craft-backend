package com.quant.craft.backend.presentation.controller.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthorizationCodeRequest {

  private String code;
  private String redirectUri;
}
