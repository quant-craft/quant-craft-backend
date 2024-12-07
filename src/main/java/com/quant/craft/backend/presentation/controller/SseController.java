package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.SseService;
import com.quant.craft.backend.domain.trade.TradingBot;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.TradingBotRepository;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    private final TradingBotRepository tradingBotRepository;

    @GetMapping(path = "/demo-tradings/{botId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter demo(@RequiredLogin User user, @PathVariable Long botId) {
        TradingBot bot = tradingBotRepository.findById(botId)
                .orElseThrow(() -> new NotFoundException("Trading bot not found"));

        if (!bot.getUserId().equals(user.getId())) {
            throw new BadRequestException("Trading bot does not belong to the user");
        }

        return sseService.createEmitter(botId);
    }
}