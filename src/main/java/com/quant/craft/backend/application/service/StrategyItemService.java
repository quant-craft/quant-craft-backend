package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.strategy.StrategyItem;
import com.quant.craft.backend.domain.strategy.StrategyItemSortOption;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.StrategyItemRepository;
import com.quant.craft.backend.presentation.controller.strategy.dto.request.StrategyItemPaginationRequest;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategyItemResponse;
import com.quant.craft.backend.presentation.controller.strategy.dto.response.StrategyItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StrategyItemService {

  private final StrategyItemRepository repository;

  public StrategyItemResponse findItem(Long strategyItemId) {
    StrategyItem item = repository.findById(strategyItemId)
        .orElseThrow(() -> new NotFoundException("strategy not found"));
    return StrategyItemResponse.from(item);
  }

  public StrategyItemsResponse findItems(StrategyItemPaginationRequest request) {
    int pageBasedIndex = request.getPage() - 1;

    Sort sort = StrategyItemSortOption.getMatchedSort(request.getSortOption());
    Page<StrategyItem> strategies = repository.findAll(
        PageRequest.of(pageBasedIndex, request.getSize(), sort));

    return new StrategyItemsResponse(
        strategies.getTotalElements(),
        strategies.getTotalPages(),
        strategies.getContent().stream()
            .map(StrategyItemResponse::from)
            .toList()
    );
  }

  public StrategyItemsResponse searchItems(StrategyItemPaginationRequest request) {
    int pageBasedIndex = request.getPage() - 1;

    Sort sort = StrategyItemSortOption.getMatchedSort(request.getSortOption());
    Page<StrategyItem> items = repository.findByNameContainingIgnoreCase(
        request.getKeyword(),
        PageRequest.of(pageBasedIndex, request.getSize(), sort)
    );

    return new StrategyItemsResponse(
        items.getTotalElements(),
        items.getTotalPages(),
        items.getContent().stream()
            .map(StrategyItemResponse::from)
            .toList()
    );
  }
}
