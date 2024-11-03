package com.quant.craft.backend.infrastructure.client.trade.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.quant.craft.backend.domain.trade.TradingBot;
import com.quant.craft.backend.domain.trade.TradingBotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TradingBotResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private Boolean dryRun;

    @Column(precision = 18, scale = 8)
    private BigDecimal cash;

    @Enumerated(EnumType.STRING)
    private TradingBotStatus status;

    private Long userId;

    private Long exchangeApiKeyId;

    private Long strategyId;

    @Column(nullable = false)
    private Long version;

    public TradingBot toDomain() {
        return TradingBot.builder()
                .id(id)
                .name(name)
                .dryRun(dryRun)
                .cash(cash)
                .status(status)
                .userId(userId)
                .exchangeApiKeyId(exchangeApiKeyId)
                .strategyId(strategyId)
                .version(version)
                .build();
    }
}
