package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.strategy.UserStrategy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStrategyRepository extends JpaRepository<UserStrategy, Long> {

  boolean existsByUserIdAndStrategyId(Long userId, Long strategyId);

  List<UserStrategy> findAllByUserId(Long userId);
}
