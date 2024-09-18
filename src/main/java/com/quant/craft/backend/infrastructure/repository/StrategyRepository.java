package com.quant.craft.backend.infrastructure.repository;


import com.quant.craft.backend.domain.strategy.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
}
