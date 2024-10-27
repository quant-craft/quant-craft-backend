package com.quant.craft.backend.domain.trade;

import com.quant.craft.backend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "trading_bots", schema = "trade")
public class TradingBot extends BaseEntity {

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
}
