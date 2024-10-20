package com.quant.craft.backend.presentation.controller.auth.dto.response;

import com.quant.craft.backend.domain.auth.ExchangeApiKey;
import com.quant.craft.backend.domain.auth.ExchangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangeApiKeyResponse {

  private Long id;

  private ExchangeType exchange;

  private String apiKey;

  private String secretKey;

  private Long userId;

  public static ExchangeApiKeyResponse from(ExchangeApiKey exchangeApiKey) {
    return new ExchangeApiKeyResponse(
        exchangeApiKey.getId(),
        exchangeApiKey.getExchange(),
        exchangeApiKey.getApiKey(),
        exchangeApiKey.getSecretKey(),
        exchangeApiKey.getUser().getId()
    );
  }
}
