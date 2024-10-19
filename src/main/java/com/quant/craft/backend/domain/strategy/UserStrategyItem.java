package com.quant.craft.backend.domain.strategy;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "user_strategy_items")
public class UserStrategyItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_strategies_user_id_fk"))
    private User user;

    @OneToOne
    @JoinColumn(name = "strategy_item_id", nullable = false, foreignKey = @ForeignKey(name = "user_strategies_strategy_item_id_fk"))
    private StrategyItem strategyItem;
}
