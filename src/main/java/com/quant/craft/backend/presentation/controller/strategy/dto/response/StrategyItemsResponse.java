package com.quant.craft.backend.presentation.controller.strategy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyItemsResponse {

    private Long totalStrategyCount;
    private Integer totalPage;
    private List<StrategyItemResponse> strategies;
}
