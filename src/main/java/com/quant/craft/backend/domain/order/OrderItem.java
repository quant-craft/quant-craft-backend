package com.quant.craft.backend.domain.order;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.strategy.StrategyItem;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "strategy_item_id", nullable = false, foreignKey = @ForeignKey(name = "order_items_strategy_item_id_fk"))
    private StrategyItem strategyItem;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "order_items_order_id_fk"))
    private Order order;
}
