package com.quant.craft.backend.infrastructure.repository;


import com.quant.craft.backend.domain.strategy.Strategy;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    @NonNull Page<Strategy> findAll(@NonNull Pageable pageable);
    Page<Strategy> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
