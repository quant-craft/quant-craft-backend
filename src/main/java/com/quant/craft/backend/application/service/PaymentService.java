package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.payment.Payment;
import com.quant.craft.backend.infrastructure.client.payment.TossPaymentsClient;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsConfirmRequest;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsPayment;
import com.quant.craft.backend.presentation.controller.payment.dto.PaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final TossPaymentsClient client;

    public Payment confirm(PaymentConfirmRequest request) {
        TossPaymentsPayment tossPaymentsPayment = client.confirm(new TossPaymentsConfirmRequest(
                request.getPaymentKey(), request.getAmount(), request.getOrderId())
        );

        return Payment.builder()
                .paymentKey(tossPaymentsPayment.getPaymentKey())
                .amount(tossPaymentsPayment.getTotalAmount())
                .orderId(tossPaymentsPayment.getOrderId())
                .build();
    }
}
