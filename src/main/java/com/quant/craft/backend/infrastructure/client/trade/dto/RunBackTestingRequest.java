package com.quant.craft.backend.infrastructure.client.trade.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RunBackTestingRequest {

    private String startDate;

    private String endDate;

    private String strategyId;

    private BigDecimal cash;

    private BigDecimal commission;
}
