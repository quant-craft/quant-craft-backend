package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.presentation.dto.payment.PaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping("/confirm")
    public String confirm(@ModelAttribute PaymentConfirmRequest request) {
        return "ok";
    }
}
