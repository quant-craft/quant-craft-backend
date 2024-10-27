package com.quant.craft.backend.presentation.controller.trade.dto.response;

import com.quant.craft.backend.domain.trade.TradingBot;
import com.quant.craft.backend.domain.trade.TradingBotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TradingBotResponse {

    private Long id;

    private String name;

    private Boolean dryRun;

    private BigDecimal cash;

    private TradingBotStatus status;

    private Long userId;

    private Long exchangeApiKeyId;

    private Long strategyId;

    private Long version;

    public static TradingBotResponse from(TradingBot tradingBot) {
        return new TradingBotResponse(
                tradingBot.getId(),
                tradingBot.getName(),
                tradingBot.getDryRun(),
                tradingBot.getCash(),
                tradingBot.getStatus(),
                tradingBot.getUserId(),
                tradingBot.getExchangeApiKeyId(),
                tradingBot.getStrategyId(),
                tradingBot.getVersion()
        );
    }
}
