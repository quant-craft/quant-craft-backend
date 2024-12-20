package com.quant.craft.backend.presentation.controller.auth.dto.request;

import com.quant.craft.backend.domain.auth.ExchangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeApiKeyRequest {

  private ExchangeType exchange;

  private String apiKey;

  private String secretKey;
}
