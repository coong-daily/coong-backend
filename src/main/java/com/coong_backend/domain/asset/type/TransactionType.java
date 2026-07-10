package com.coong_backend.domain.asset.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
    INCOME("수입"),
    EXPENSE("지출");

    private final String description;

    // 생성자
    TransactionType(String description) {
        this.description = description;
    }

    // 한글명을 가져오고 싶을 때 사용
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static TransactionType from(String value) {
        if (value == null) return null;
        try {
            return TransactionType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 거래 타입입니다: " + value);
        }
    }
}