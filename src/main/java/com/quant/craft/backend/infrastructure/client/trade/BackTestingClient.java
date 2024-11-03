package com.quant.craft.backend.infrastructure.client.trade;

import com.quant.craft.backend.domain.trade.BackTesting;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.trade.dto.BackTestingResponse;
import com.quant.craft.backend.infrastructure.client.trade.dto.RunBackTestingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class BackTestingClient {

    private final RestClient client;

    @Value("${application.host.trade}")
    private String apiServerUrl;

    public BackTesting run(RunBackTestingRequest request) {
        try {
            String url = String.format("%s/backtestings/run", apiServerUrl);
            BackTestingResponse response = client.post()
                    .uri(url)
                    .body(request)
                    .retrieve()
                    .body(BackTestingResponse.class);

            if (response == null) {
                throw new BadRequestException("BackTesting cannot be null!");
            }

            return response.toDomain();
        } catch (Exception e) {
            throw new BadRequestException("Run BackTesting Error. e: " + e);
        }
    }
}
