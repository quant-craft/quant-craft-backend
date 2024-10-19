package com.quant.craft.backend.presentation.controller.user.dto;

import com.quant.craft.backend.domain.strategy.Strategy;
import com.quant.craft.backend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserStrategyResponse {

    private User user;

    private List<Strategy> strategies;
}
