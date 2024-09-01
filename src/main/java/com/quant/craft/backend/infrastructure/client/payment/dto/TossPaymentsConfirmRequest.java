package com.quant.craft.backend.infrastructure.client.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TossPaymentsConfirmRequest {

    private String paymentKey;

    private Long amount;

    private String orderId;
}
