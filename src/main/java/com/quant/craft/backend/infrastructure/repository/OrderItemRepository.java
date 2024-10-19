package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.order.OrderItem;
import com.quant.craft.backend.domain.strategy.StrategyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(Long orderId);
}
