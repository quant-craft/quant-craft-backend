package com.quant.craft.backend.presentation.controller.user.dto;

import com.quant.craft.backend.domain.auth.OAuthProvider;
import com.quant.craft.backend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

  private String nickname;

  private String email;

  private OAuthProvider oauthProvider;

  private Long point;

  public static UserResponse from(User user) {
    return new UserResponse(
        user.getNickname(),
        user.getEmail(),
        user.getOauthProvider(),
        user.getPoint()
    );
  }
}
