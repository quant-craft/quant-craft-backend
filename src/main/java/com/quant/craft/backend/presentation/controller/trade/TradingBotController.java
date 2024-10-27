package com.quant.craft.backend.presentation.controller.trade;

import com.quant.craft.backend.application.service.TradingBotService;
import com.quant.craft.backend.presentation.controller.trade.dto.request.CreateTradingBotRequest;
import com.quant.craft.backend.presentation.controller.trade.dto.response.TradingBotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trading-bots")
public class TradingBotController {

    private final TradingBotService service;

    @GetMapping("/{tradingBotId}")
    public ResponseEntity<TradingBotResponse> read(@PathVariable Long tradingBotId) {
        return ResponseEntity.ok(service.find(tradingBotId));
    }

    @PostMapping
    public ResponseEntity<TradingBotResponse> create(@RequestBody CreateTradingBotRequest request) {
        return ResponseEntity.ok(service.createTradingBot(request));
    }

    @DeleteMapping("/{tradingBotId}")
    public ResponseEntity<Void> delete(@PathVariable Long tradingBotId) {
        service.deleteTradingBot(tradingBotId);
        return ResponseEntity.noContent().build();
    }
}
