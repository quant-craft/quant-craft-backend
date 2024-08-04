package com.quant.craft.backend.domain;

import java.util.Arrays;

public enum OAuthProvider {
    KAKAO;

    public static OAuthProvider from(String name) {
        return Arrays.stream(values())
                .filter(provider -> provider.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not Found Provider. provider: " + name));
    }
}
