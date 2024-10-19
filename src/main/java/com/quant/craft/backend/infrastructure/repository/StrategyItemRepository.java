package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.strategy.StrategyItem;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrategyItemRepository extends JpaRepository<StrategyItem, Long> {

    @NonNull Page<StrategyItem> findAll(@NonNull Pageable pageable);
    Page<StrategyItem> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
