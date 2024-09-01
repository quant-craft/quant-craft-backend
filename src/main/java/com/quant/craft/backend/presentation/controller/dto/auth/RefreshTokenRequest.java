package com.quant.craft.backend.presentation.controller.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshTokenRequest {

    private String refreshToken;
}
