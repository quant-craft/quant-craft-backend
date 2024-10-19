package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.order.Order;
import com.quant.craft.backend.domain.strategy.StrategyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);
}
