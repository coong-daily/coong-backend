package com.coong_backend.domain.asset.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ExpenseCategory {
    SHOPPING("쇼핑"),
    FOOD("식비"),
    TRANSPORT("교통"),
    SUBSCRIPTION("구독"),
    EXERCISE("운동"),
    ETC("기타");

    private final String description;

    ExpenseCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static ExpenseCategory from(String value) {
        if (value == null) return null;

        try {
            return ExpenseCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            for (ExpenseCategory category : ExpenseCategory.values()) {
                if (category.getDescription().equals(value)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("올바르지 않은 지출 카테고리입니다: " + value);
        }
    }
}