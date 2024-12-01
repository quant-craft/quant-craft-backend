package com.quant.craft.backend.presentation.controller.trade.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RunBackTestingRequest {

    private String startDate;

    private String endDate;

    private String strategyId;

    private BigDecimal cash;

    private BigDecimal commission;
}
