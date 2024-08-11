package com.quant.craft.backend.infrastructure.client.dto;

import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserDTO {

    private String nickname;

    private String oauthId;

    private OAuthProvider oauthProvider;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .build();
    }
}
