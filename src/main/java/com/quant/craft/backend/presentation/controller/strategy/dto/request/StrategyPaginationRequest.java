package com.quant.craft.backend.presentation.controller.strategy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyPaginationRequest {

    private String sortOption;

    private Integer page;

    private Integer size;
}
