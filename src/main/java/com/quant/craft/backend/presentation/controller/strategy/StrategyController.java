package com.quant.craft.backend.presentation.controller.strategy;

import com.quant.craft.backend.application.service.StrategyService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategiesResponse;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategyResponse;
import com.quant.craft.backend.presentation.controller.strategy.dto.request.StrategyPaginationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/strategies")
public class StrategyController {

    private final StrategyService service;

    @GetMapping("/{strategyId}")
    public ResponseEntity<StrategyResponse> viewStrategy(@PathVariable Long strategyId) {
        return ResponseEntity.ok(service.findStrategy(strategyId));
    }

    @PostMapping("/{strategyId}/buy")
    public ResponseEntity<Void> buyStrategy(@RequiredLogin User user, @PathVariable Long strategyId) {
        service.buyStrategy(user, strategyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paging")
    public ResponseEntity<StrategiesResponse> listStrategies(@ModelAttribute StrategyPaginationRequest request) {
        return ResponseEntity.ok(service.findStrategies(request));
    }

    @GetMapping("/search")
    public ResponseEntity<StrategiesResponse> searchStrategies(@ModelAttribute StrategyPaginationRequest request) {
        return ResponseEntity.ok(service.searchStrategies(request));
    }
}
