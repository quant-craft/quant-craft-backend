package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.trade.BackTesting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackTestingRepository extends JpaRepository<BackTesting, Long> {
}
