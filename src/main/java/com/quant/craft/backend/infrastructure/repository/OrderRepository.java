package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.order.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByUserId(Long userId);
}
