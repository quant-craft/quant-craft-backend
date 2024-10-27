package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.trade.TradingBot;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.client.trade.TradingBotClient;
import com.quant.craft.backend.infrastructure.repository.TradingBotRepository;
import com.quant.craft.backend.presentation.controller.trade.dto.request.CreateTradingBotRequest;
import com.quant.craft.backend.presentation.controller.trade.dto.response.TradingBotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TradingBotService {

    private final TradingBotClient client;

    private final TradingBotRepository repository;

    public TradingBotResponse createTradingBot(CreateTradingBotRequest request) {
        TradingBot tradingBot = client.create(
                new com.quant.craft.backend.infrastructure.client.trade.dto.CreateTradingBotRequest(
                        request.getName(),
                        request.isDryRun(),
                        request.getCash(),
                        request.getUserId(),
                        request.getExchangeApiKeyId(),
                        request.getStrategyId(),
                        request.getStatus()
                )
        );
        return TradingBotResponse.from(tradingBot);
    }

    public TradingBotResponse find(Long tradingBotId) {
        TradingBot tradingBot = repository.findById(tradingBotId)
                .orElseThrow(() -> new NotFoundException("TradingBot not found"));
        return TradingBotResponse.from(tradingBot);
    }

    public List<TradingBotResponse> findAllByUser(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(TradingBotResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteTradingBot(Long tradingBotId) {
        client.delete(tradingBotId);
    }
}
