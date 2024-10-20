package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.order.Order;
import com.quant.craft.backend.domain.order.OrderItem;
import com.quant.craft.backend.domain.order.OrderStatus;
import com.quant.craft.backend.domain.point.PointTxn;
import com.quant.craft.backend.domain.point.PointTxnStatus;
import com.quant.craft.backend.domain.strategy.StrategyItem;
import com.quant.craft.backend.domain.strategy.UserStrategy;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.OrderItemRepository;
import com.quant.craft.backend.infrastructure.repository.OrderRepository;
import com.quant.craft.backend.infrastructure.repository.PointTxnRepository;
import com.quant.craft.backend.infrastructure.repository.StrategyItemRepository;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.infrastructure.repository.UserStrategyRepository;
import com.quant.craft.backend.presentation.controller.order.dto.response.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final OrderRepository repository;

  private final OrderItemRepository orderItemRepository;

  private final StrategyItemRepository strategyItemRepository;

  private final UserRepository userRepository;

  private final UserStrategyRepository userStrategyRepository;

  private final PointTxnRepository pointTxnRepository;

  public List<OrderResponse> list(User user) {
    return repository.findAllByUserId(user.getId()).stream()
        .map(order -> {
          List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
          if (orderItems.isEmpty()) {
            throw new NotFoundException("order item not found");
          }
          return OrderResponse.from(order, orderItems.get(0));
        })
        .toList();
  }

  @Transactional
  public OrderResponse buyStrategyItem(User user, Long strategyItemId) {
    StrategyItem strategyItem = strategyItemRepository.findById(strategyItemId)
        .orElseThrow(() -> new NotFoundException("strategy item not found"));

    if (userStrategyRepository.existsByUserIdAndStrategyId(user.getId(),
        strategyItem.getStrategy().getId())) {
      throw new BadRequestException("already bought");
    }

    if (user.getPoint() - strategyItem.getPrice() < 0) {
      throw new BadRequestException("not enough point");
    }

    user.updatePoint(user.getPoint() - strategyItem.getPrice());
    userRepository.save(user);

    pointTxnRepository.save(
        PointTxn.builder()
            .user(user)
            .point(strategyItem.getPrice())
            .status(PointTxnStatus.USE)
            .build()
    );

    userStrategyRepository.save(
        UserStrategy.builder()
            .user(user)
            .strategy(strategyItem.getStrategy())
            .build()
    );

    Order order = repository.save(
        Order.builder()
            .status(OrderStatus.DONE)
            .totalPrice(strategyItem.getPrice())
            .user(user)
            .build()
    );

    OrderItem orderItem = orderItemRepository.save(
        OrderItem.builder()
            .order(order)
            .strategyItem(strategyItem)
            .build()
    );

    return OrderResponse.from(order, orderItem);
  }
}
