package com.quant.craft.backend.presentation.controller.payment.dto;

import com.quant.craft.backend.domain.payment.PaymentTxn;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTxnResponse {

  private Long id;

  private Long userId;

  private BigDecimal amount;

  private String status;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static PaymentTxnResponse from(PaymentTxn paymentTxn) {
    return new PaymentTxnResponse(
        paymentTxn.getId(),
        paymentTxn.getUser().getId(),
        paymentTxn.getAmount(),
        paymentTxn.getStatus().name(),
        paymentTxn.getCreatedAt(),
        paymentTxn.getUpdatedAt()
    );
  }
}
