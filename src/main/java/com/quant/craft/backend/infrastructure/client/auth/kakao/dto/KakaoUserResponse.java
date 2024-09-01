package com.quant.craft.backend.infrastructure.client.auth.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserResponse {

    private String id;

    private KakaoAccount kakaoAccount;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {

        private KakaoProfile profile;

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class KakaoProfile {

            private String nickname;
        }
    }
}
