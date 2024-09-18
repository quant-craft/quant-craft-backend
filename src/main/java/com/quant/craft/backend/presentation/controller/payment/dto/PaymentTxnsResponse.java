package com.quant.craft.backend.presentation.controller.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTxnsResponse {

    private List<PaymentTxnResponse> paymentTxns;
}
