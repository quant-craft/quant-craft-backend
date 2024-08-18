package com.quant.craft.backend.domain.auth;

import com.quant.craft.backend.exception.NotFoundException;

import java.util.Arrays;

public enum OAuthProvider {
    KAKAO,
    GOOGLE;

    public static OAuthProvider from(String name) {
        return Arrays.stream(values())
                .filter(provider -> provider.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Not Found Provider. provider: " + name));
    }
}
