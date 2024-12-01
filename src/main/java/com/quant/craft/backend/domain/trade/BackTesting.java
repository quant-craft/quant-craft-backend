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
@Table(name = "backtesting", schema = "trade")
public class BackTesting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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

    @Column(columnDefinition = "TEXT")
    private String trades;

    @Column(columnDefinition = "TEXT")
    private String equityCurve;
}
