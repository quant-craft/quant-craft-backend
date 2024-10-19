package com.quant.craft.backend.domain.strategy;

import com.quant.craft.backend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "strategies", schema = "trade")
public class Strategy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String description;

    private float leverage;

    private boolean exclusiveOrders;

    private boolean hedgeMode;

    private String timeframe;

    private String symbol;

    private String exchange;
}
