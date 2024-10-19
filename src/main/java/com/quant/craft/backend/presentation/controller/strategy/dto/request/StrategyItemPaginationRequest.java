package com.quant.craft.backend.presentation.controller.strategy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyItemPaginationRequest {

    private String sortOption;

    private String keyword;

    private Integer page = 1;

    private Integer size = 10;
}
