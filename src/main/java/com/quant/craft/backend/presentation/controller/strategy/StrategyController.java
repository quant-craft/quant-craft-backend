package com.quant.craft.backend.presentation.controller.strategy;

import com.quant.craft.backend.application.service.StrategyService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/strategy")
public class StrategyController {

    private final StrategyService service;

    @GetMapping("/{strategyId}")
    public void viewStrategy(@PathVariable Long strategyId) {
        service.findStrategy(strategyId);
    }

    @PostMapping("/{strategyId}/buy")
    public void buyStrategy(@RequiredLogin User user, @PathVariable Long strategyId) {
        service.buyStrategy(user, strategyId);
    }
}
