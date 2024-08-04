package com.quant.craft.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String nickname;

    private String email;

    private String oauthId;

    private String oauthProvider;

    private String refreshToken;

    public void updateEmail(String email) {
        this.email = email;
    }
}
