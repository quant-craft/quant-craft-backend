package com.quant.craft.backend.domain.user;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String email;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;

    @Check(constraints = "point >= 0")
    private Long point = 0L;

    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
