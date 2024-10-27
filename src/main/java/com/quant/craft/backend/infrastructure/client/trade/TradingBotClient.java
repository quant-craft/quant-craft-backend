package com.quant.craft.backend.infrastructure.client.trade;

import com.quant.craft.backend.domain.trade.TradingBot;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.trade.dto.CreateTradingBotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class TradingBotClient {

  private final RestClient client;

  @Value("${application.host.trade}")
  private String apiServerUrl;

  public TradingBot create(CreateTradingBotRequest request) {
    try {
      String url = String.format("%s/trading-bots", apiServerUrl);
      TradingBot response = client.post()
              .uri(url)
              .body(request)
              .retrieve()
              .body(TradingBot.class);

        if (response == null) {
            throw new BadRequestException("TradingBot cannot be null!");
        }

        return response;
    } catch (Exception e) {
      throw new BadRequestException("Create TradingBot Error. e: " + e);
    }
  }

  public void delete(Long tradingBotId) {
    try {
      String url = String.format("%s/trading-bots/%d", apiServerUrl, tradingBotId);
      client.delete()
              .uri(url)
              .retrieve()
              .body(Void.class);
    } catch (Exception e) {
      throw new BadRequestException("Delete TradingBot Error. e: " + e);
    }
  }
}
