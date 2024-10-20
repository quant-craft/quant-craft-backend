package com.quant.craft.backend.presentation.controller.strategy.dto.response;

import com.quant.craft.backend.domain.strategy.Strategy;
import com.quant.craft.backend.domain.strategy.StrategyItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StrategyItemResponse {

  private Long id;

  private String name;

  private String description;

  private Long price;

  private Strategy strategy;

  public static StrategyItemResponse from(StrategyItem strategyItem) {
    return new StrategyItemResponse(
        strategyItem.getId(),
        strategyItem.getName(),
        strategyItem.getDescription(),
        strategyItem.getPrice(),
        strategyItem.getStrategy()
    );
  }
}
