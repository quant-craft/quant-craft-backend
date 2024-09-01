package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.payment.PaymentTxn;
import com.quant.craft.backend.domain.payment.PaymentTxnStatus;
import com.quant.craft.backend.domain.point.PointTxnStatus;
import com.quant.craft.backend.domain.point.PointTxn;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.infrastructure.client.payment.TossPaymentsClient;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsConfirmRequest;
import com.quant.craft.backend.infrastructure.client.payment.dto.TossPaymentsPayment;
import com.quant.craft.backend.infrastructure.repository.PaymentTxnRepository;
import com.quant.craft.backend.infrastructure.repository.PointTxnRepository;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.presentation.controller.point.dto.PointChargeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PointService {

    private final TossPaymentsClient client;
    private final PointTxnRepository pointTxnRepository;
    private final PaymentTxnRepository paymentTxnRepository;
    private final UserRepository userRepository;

    @Transactional
    public void charge(User user, PointChargeRequest request) {
        String orderId = UUID.randomUUID().toString();

        TossPaymentsPayment tossPaymentsPayment = client.confirm(new TossPaymentsConfirmRequest(
                request.getPaymentKey(), request.getAmount(), orderId)
        );

        PointTxn pointTxn = pointTxnRepository.save(PointTxn.builder()
                .user(user)
                .point(request.getAmount())
                .status(PointTxnStatus.CHARGE)
                .build()
        );

        paymentTxnRepository.save(PaymentTxn.builder()
                .amount(tossPaymentsPayment.getTotalAmount())
                .orderId(tossPaymentsPayment.getOrderId())
                .paymentKey(tossPaymentsPayment.getPaymentKey())
                .status(PaymentTxnStatus.DONE)
                .pointTxn(pointTxn)
                .build()
        );

        user.updatePoint(user.getPoint() + request.getAmount());
        userRepository.save(user);
    }
}
