package com.quant.craft.backend.infrastructure.client.trade;

import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.trade.dto.CreateStrategyRequest;
import com.quant.craft.backend.infrastructure.client.trade.dto.Strategy;
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

    public Strategy create(CreateStrategyRequest request) {
        try {
            String url = String.format("%s/strategies", apiServerUrl);
            Strategy response = client.post()
                    .uri(url)
                    .body(request)
                    .retrieve()
                    .body(Strategy.class);

            if (response == null) {
                throw new BadRequestException("Strategy cannot be null!");
            }

            return response;
        } catch (Exception e) {
            throw new BadRequestException("Create Strategy Error. e: " + e);
        }
    }
}
