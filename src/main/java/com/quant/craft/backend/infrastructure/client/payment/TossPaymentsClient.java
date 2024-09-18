package com.quant.craft.backend.infrastructure.client.payment;

import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsConfirmRequest;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TossPaymentsClient {

    private static final String BASIC_PREFIX = "Basic ";

    private final RestClient client;

    @Value("${toss-payments.secret-key}")
    private String secretKey;

    @Value("${toss-payments.api-server-url}")
    private String apiServerUrl;

    public TossPaymentsPayment confirm(TossPaymentsConfirmRequest request) {
        try {
            String url = String.format("%s/payments/confirm", apiServerUrl);
            TossPaymentsPayment response = client.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, buildBasicSecretKey())
                    .body(request)
                    .retrieve()
                    .body(TossPaymentsPayment.class);

            if (response == null) {
                throw new BadRequestException("PaymentResponse cannot be null!");
            }

            if (response.getStatus() != TossPaymentsPayment.TossPaymentsPaymentStatus.DONE) {
                throw new BadRequestException("Confirm Payment Error. status: " + response.getStatus());
            }

            Optional.ofNullable(response.getFailure()).ifPresent(failure -> {
                throw new BadRequestException("Confirm Payment Error. failure: " + failure);
            });

            return response;
        } catch (Exception e) {
            throw new BadRequestException("Confirm Payment Error. e: " + e);
        }
    }

    private String buildBasicSecretKey() {
        String input = secretKey + ":";
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return BASIC_PREFIX + new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
