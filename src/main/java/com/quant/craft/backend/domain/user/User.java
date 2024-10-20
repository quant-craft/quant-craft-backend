package com.quant.craft.backend.domain.user;

import com.quant.craft.backend.domain.BaseEntity;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String nickname;

  private String email;

  @Column(nullable = false)
  private String oauthId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OAuthProvider oauthProvider;

  @Builder.Default
  @Check(constraints = "point >= 0")
  @Column(nullable = false)
  private Long point = 0L;

  private String refreshToken;

  public void updatePoint(Long point) {
    this.point = point;
  }

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
