package com.quant.craft.backend.presentation.controller.auth.dto.request;

import com.quant.craft.backend.domain.auth.ExchangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExchangeApiKeyRequest {

  private ExchangeType exchange;

  private String apiKey;

  private String secretKey;
}
