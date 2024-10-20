package com.quant.craft.backend.infrastructure.client.trade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class TradeBotClient {

  private final RestClient client;

  @Value("${application.host.trade}")
  private String apiServerUrl;
}
