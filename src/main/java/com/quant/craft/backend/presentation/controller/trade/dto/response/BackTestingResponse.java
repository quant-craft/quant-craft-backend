package com.quant.craft.backend.presentation.controller.trade.dto.response;

import com.quant.craft.backend.domain.trade.BackTesting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    public static BackTestingResponse from(BackTesting backTesting) {
        return new BackTestingResponse(
                backTesting.getId(),
                backTesting.getStrategyId(),
                backTesting.getStrategyName(),
                backTesting.getStartDate(),
                backTesting.getEndDate(),
                backTesting.getInitialCapital(),
                backTesting.getFinalEquity(),
                backTesting.getTotalReturn(),
                backTesting.getMaxDrawdown(),
                backTesting.getWinRate(),
                backTesting.getProfitFactor(),
                backTesting.getTotalTrades(),
                backTesting.getTrades(),
                backTesting.getEquityCurve()
        );
    }
}
