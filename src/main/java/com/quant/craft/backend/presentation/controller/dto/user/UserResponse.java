package com.quant.craft.backend.presentation.controller.dto.user;

import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.domain.auth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

    private String oauthId;

    private String nickname;

    private String email;

    private OAuthProvider oauthProvider;

    private Long point;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getOauthId(),
                user.getNickname(),
                user.getEmail(),
                user.getOauthProvider(),
                user.getPoint()
        );
    }
}
