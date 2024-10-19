package com.quant.craft.backend.domain.strategy;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "strategy_items")
public class StrategyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long price;

    @OneToOne
    @JoinColumn(name = "strategy_id", nullable = false, foreignKey = @ForeignKey(name = "strategy_items_strategy_id_fk"))
    private Strategy strategy;
}
