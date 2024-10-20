package com.quant.craft.backend.presentation.controller.order;

import com.quant.craft.backend.application.service.OrderService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.order.dto.response.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService service;

  @GetMapping
  public ResponseEntity<List<OrderResponse>> list(@RequiredLogin User user) {
    return ResponseEntity.ok(service.list(user));
  }

  @PostMapping("/strategy-items/{strategyItemId}")
  public ResponseEntity<OrderResponse> buyStrategyItem(@RequiredLogin User user,
      @PathVariable Long strategyItemId) {
    return ResponseEntity.ok(service.buyStrategyItem(user, strategyItemId));
  }
}
