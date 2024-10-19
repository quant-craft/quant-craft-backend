package com.quant.craft.backend.infrastructure.client.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Strategy {

    private Long id;

    private String name;

    private String description;

    private float leverage;

    private boolean exclusiveOrders;

    private boolean hedgeMode;

    private String timeframe;

    private String symbol;

    private String exchange;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
