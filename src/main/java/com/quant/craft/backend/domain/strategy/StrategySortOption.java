package com.quant.craft.backend.domain.strategy;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum StrategySortOption {

    LATEST(Sort.by(Sort.Direction.DESC, "id")),

    PRICE_DESC(Sort.by(Sort.Direction.DESC, "price").and(Sort.by("id"))),

    PRICE_ASC(Sort.by(Sort.Direction.ASC, "price").and(Sort.by("id"))),

    DEFAULT(Sort.by(Sort.Direction.ASC, "id"));

    private final Sort sort;

    public static Sort getMatchedSort(String sorting) {
        if (Objects.isNull(sorting)) {
            return DEFAULT.sort;
        }
        return Arrays.stream(values())
                .filter(sortOption -> sortOption.name().equals(sorting.toUpperCase(Locale.ROOT)))
                .findFirst()
                .orElse(DEFAULT)
                .sort;
    }
}
