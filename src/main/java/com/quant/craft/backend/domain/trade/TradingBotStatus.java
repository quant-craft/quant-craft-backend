package com.quant.craft.backend.domain.trade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradingBotStatus {
    PENDING("대기중"),
    RUNNING("실행중"),
    STOPPING("중지중"),
    STOPPED("중지됨");

    private final String value;
}
