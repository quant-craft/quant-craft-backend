package com.quant.craft.backend.presentation.controller.payment;

import com.quant.craft.backend.application.service.PaymentService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.payment.dto.PaymentTxnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentService service;

  @GetMapping("/txns")
  public ResponseEntity<PaymentTxnsResponse> viewPointTxns(@RequiredLogin User user) {
    return ResponseEntity.ok(service.findPaymentTxns(user));
  }
}
