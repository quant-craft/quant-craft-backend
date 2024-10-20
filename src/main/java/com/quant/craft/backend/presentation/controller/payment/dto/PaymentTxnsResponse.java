package com.quant.craft.backend.presentation.controller.payment.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTxnsResponse {

  private List<PaymentTxnResponse> paymentTxns;
}
