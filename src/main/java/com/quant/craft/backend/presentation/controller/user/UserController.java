package com.quant.craft.backend.presentation.controller.user;


import com.quant.craft.backend.application.service.TradingBotService;
import com.quant.craft.backend.application.service.UserService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.trade.dto.response.TradingBotResponse;
import com.quant.craft.backend.presentation.controller.user.dto.UserResponse;
import com.quant.craft.backend.presentation.controller.user.dto.UserStrategyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService service;

  private final TradingBotService tradingBotService;

  @GetMapping
  public ResponseEntity<UserResponse> readUserProfile(@RequiredLogin User user) {
    return ResponseEntity.ok(service.findByUser(user.getId()));
  }

  @GetMapping("/strategies")
  public ResponseEntity<UserStrategyResponse> readUserStrategies(@RequiredLogin User user) {
    return ResponseEntity.ok(service.findUserStrategies(user.getId()));
  }

  @GetMapping("/trading-bots")
  public ResponseEntity<List<TradingBotResponse>> readUserTradingBots(@RequiredLogin User user) {
    return ResponseEntity.ok(tradingBotService.findAllByUser(user.getId()));
  }
}
