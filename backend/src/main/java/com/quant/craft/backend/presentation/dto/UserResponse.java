package com.quant.craft.backend.presentation.dto;

import com.quant.craft.backend.domain.User;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

    private Long id;

    private String nickname;

    private String email;

    private OAuthProvider oauthProvider;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getOauthProvider()
        );
    }
}
