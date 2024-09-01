package com.quant.craft.backend.domain.payment;

import com.quant.craft.backend.domain.point.PointTxn;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "payment_txns")
public class PaymentTxn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentTxnStatus status;

    @OneToOne
    @JoinColumn(name = "point_txn_id", nullable = false)
    private PointTxn pointTxn;
}
