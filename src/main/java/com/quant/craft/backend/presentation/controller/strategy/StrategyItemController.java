package com.quant.craft.backend.presentation.controller.strategy;

import com.quant.craft.backend.application.service.StrategyItemService;
import com.quant.craft.backend.presentation.controller.strategy.dto.request.StrategyItemPaginationRequest;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategyItemResponse;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategyItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/strategy-items")
public class StrategyItemController {

  private final StrategyItemService service;

  @GetMapping("/{strategyItemId}")
  public ResponseEntity<StrategyItemResponse> read(@PathVariable Long strategyItemId) {
    return ResponseEntity.ok(service.findItem(strategyItemId));
  }

  @GetMapping("/paging")
  public ResponseEntity<StrategyItemsResponse> list(
      @ModelAttribute StrategyItemPaginationRequest request) {
    return ResponseEntity.ok(service.findItems(request));
  }

  @GetMapping("/search")
  public ResponseEntity<StrategyItemsResponse> search(
      @ModelAttribute StrategyItemPaginationRequest request) {
    return ResponseEntity.ok(service.searchItems(request));
  }
}
