package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.payment.PaymentTxn;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.infrastructure.repository.PaymentTxnRepository;
import com.quant.craft.backend.presentation.controller.payment.dto.PaymentTxnResponse;
import com.quant.craft.backend.presentation.controller.payment.dto.PaymentTxnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentTxnRepository paymentTxnRepository;

    public PaymentTxnsResponse findPaymentTxns(User user) {
        List<PaymentTxn> paymentTxns = paymentTxnRepository.findAllByUserId(user.getId());
        return new PaymentTxnsResponse(paymentTxns.stream()
                .map(PaymentTxnResponse::from)
                .toList());
    }
}
