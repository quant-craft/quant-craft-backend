package com.quant.craft.backend.domain.payment;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.point.PointTxn;
import com.quant.craft.backend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "payment_txns")
public class PaymentTxn extends BaseEntity {

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

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "payment_txns_user_id_fk"))
  private User user;

  @OneToOne
  @JoinColumn(name = "point_txn_id", nullable = false, foreignKey = @ForeignKey(name = "payment_txns_point_txn_id_fk"))
  private PointTxn pointTxn;
}
