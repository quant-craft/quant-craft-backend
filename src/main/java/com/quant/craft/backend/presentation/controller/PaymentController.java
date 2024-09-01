package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.PaymentService;
import com.quant.craft.backend.presentation.controller.dto.payment.PaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@ModelAttribute PaymentConfirmRequest request) {
        service.confirm(request);
        return ResponseEntity.ok().build();
    }
}
