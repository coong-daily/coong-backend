package com.coong_backend.domain.asset.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum IncomeCategory {
    SALARY("급여"),
    INTEREST("이자"),
    SIDE_INCOME("부수입"),
    BONUS("보너스"),
    ETC("기타");

    private final String description;

    IncomeCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static IncomeCategory from(String value) {
        if (value == null) return null;

        try {
            return IncomeCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            for (IncomeCategory category : IncomeCategory.values()) {
                if (category.getDescription().equals(value)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("올바르지 않은 수입 카테고리입니다: " + value);
        }
    }
}