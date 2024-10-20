package com.quant.craft.backend.presentation.controller.strategy.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyItemsResponse {

  private Long totalStrategyCount;
  private Integer totalPage;
  private List<StrategyItemResponse> strategies;
}
