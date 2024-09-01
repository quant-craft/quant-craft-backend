package com.quant.craft.backend.infrastructure.client.payment.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TossPaymentsPayment {

    private String paymentKey;

    private String orderId;

    private BigDecimal totalAmount;

    private TossPaymentsPaymentStatus status;

    @Nullable
    private Instant approvedAt;

    @Nullable
    private TossPaymentsFailure failure;

    public enum TossPaymentsPaymentStatus {

        READY,
        IN_PROGRESS,
        WAITING_FOR_DEPOSIT,
        DONE,
        CANCELED,
        PARTIAL_CANCELED,
        ABORTED,
        EXPIRED;
    }

    public static class TossPaymentsFailure {

        private String code;
        private String message;
    }
}
