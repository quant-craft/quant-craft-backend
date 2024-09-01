package com.quant.craft.backend.presentation.dto.auth;

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
