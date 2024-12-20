package com.quant.craft.backend.presentation.controller.trade.dto.request;

import com.quant.craft.backend.domain.trade.TradingBotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateTradingBotRequest {

    private String name;

    private boolean dryRun;

    private BigDecimal cash;

    private Long exchangeApiKeyId;

    private Long strategyId;

    private TradingBotStatus status;
}
