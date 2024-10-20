package com.quant.craft.backend.domain.strategy;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "user_strategies")
public class UserStrategy extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "user_strategies_user_id_fk"))
  private User user;

  @OneToOne
  @JoinColumn(name = "strategy_id", nullable = false, foreignKey = @ForeignKey(name = "user_strategies_strategy_item_id_fk"))
  private Strategy strategy;
}
