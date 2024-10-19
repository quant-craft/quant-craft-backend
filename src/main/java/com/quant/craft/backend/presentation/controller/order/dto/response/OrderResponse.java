package com.quant.craft.backend.presentation.controller.order.dto.response;

import com.quant.craft.backend.domain.order.Order;
import com.quant.craft.backend.domain.order.OrderItem;
import com.quant.craft.backend.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderResponse {

    private Long id;

    private OrderStatus status;

    private Long totalPrice;

    private OrderItem orderItem;

    private LocalDateTime createdAt;

    private LocalDateTime canceledAt;

    public static OrderResponse from(Order order, OrderItem item) {
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice(),
                item,
                order.getCreatedAt(),
                order.getCanceledAt()
        );
    }
}
