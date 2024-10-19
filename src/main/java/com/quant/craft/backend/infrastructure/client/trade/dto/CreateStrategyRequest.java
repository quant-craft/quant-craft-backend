package com.quant.craft.backend.infrastructure.client.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateStrategyRequest {

    private String name;

    private String description;

    private float leverage;

    private boolean exclusiveOrders;

    private boolean hedgeMode;

    private String timeframe;

    private String symbol;

    private String exchange;
}
