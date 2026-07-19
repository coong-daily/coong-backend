package com.coong_backend.domain.schedule.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TodoPriority {
    HIGH("높음"),
    MEDIUM("중간"),
    LOW("낮음");

    private final String description;

    TodoPriority(String description) {
        this.description = description;
    }

    @JsonCreator
    public static TodoPriority from(String value) {
        if (value == null) return null;
        try {
            return TodoPriority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 우선순위입니다: " + value);
        }
    }
}