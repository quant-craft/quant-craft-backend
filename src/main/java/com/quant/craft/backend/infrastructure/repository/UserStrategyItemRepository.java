package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.strategy.UserStrategyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStrategyItemRepository extends JpaRepository<UserStrategyItem, Long> {

    boolean existsByUserIdAndStrategyItemId(Long userId, Long strategyItemId);
}
