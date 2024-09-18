package com.quant.craft.backend.presentation.controller.strategy.dto.response;

import com.quant.craft.backend.domain.strategy.Strategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StrategyResponse {

    private Long id;

    private String name;

    private String description;

    private Long price;

    private String path;

    public static StrategyResponse from(Strategy strategy) {
        return new StrategyResponse(
                strategy.getId(),
                strategy.getName(),
                strategy.getDescription(),
                strategy.getPrice(),
                strategy.getPath()
        );
    }
}
