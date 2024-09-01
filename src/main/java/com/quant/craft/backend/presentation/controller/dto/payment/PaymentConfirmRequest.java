package com.quant.craft.backend.presentation.controller.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentConfirmRequest {

    private String paymentKey;

    private Long amount;

    private String orderId;
}
