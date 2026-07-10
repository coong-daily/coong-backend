package com.coong_backend.domain.asset.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum AccountType {
    CHECKING("입출금"),
    SAVINGS("저축"),
    INVESTMENT("투자"),
    EMERGENCY("비상금"),
    PENSION("연금");

    // 생성자
    AccountType(String description) {
        this.description = description;
    }

    // 한글명을 가져오고 싶을 때 사용
    private final String description;

    @JsonCreator
    public static AccountType from(String value) {
        if (value == null) return null;
        try {
            return AccountType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 계좌 타입입니다: " + value);
        }
    }

}