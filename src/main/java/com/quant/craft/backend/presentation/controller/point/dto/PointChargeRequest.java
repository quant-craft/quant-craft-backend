package com.quant.craft.backend.presentation.controller.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointChargeRequest {

    private String paymentKey;

    private Long amount;
}
