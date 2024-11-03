package com.quant.craft.backend.infrastructure.client.trade.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.quant.craft.backend.domain.trade.BackTesting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BackTestingResponse {

    private Long id;

    private String strategyId;

    private String strategyName;

    private String startDate;

    private String endDate;

    private BigDecimal initialCapital;

    private BigDecimal finalEquity;

    private BigDecimal totalReturn;

    private BigDecimal maxDrawdown;

    private BigDecimal winRate;

    private BigDecimal profitFactor;

    private int totalTrades;

    private String trades;

    private String equityCurve;

    public BackTesting toDomain() {
        return BackTesting.builder()
                .id(id)
                .strategyId(strategyId)
                .strategyName(strategyName)
                .startDate(startDate)
                .endDate(endDate)
                .initialCapital(initialCapital)
                .finalEquity(finalEquity)
                .totalReturn(totalReturn)
                .maxDrawdown(maxDrawdown)
                .winRate(winRate)
                .profitFactor(profitFactor)
                .totalTrades(totalTrades)
                .trades(trades)
                .equityCurve(equityCurve)
                .build();
    }
}
