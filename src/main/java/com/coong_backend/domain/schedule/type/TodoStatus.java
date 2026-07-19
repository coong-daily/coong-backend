package com.coong_backend.domain.schedule.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TodoStatus {
    PENDING("대기 중"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료됨");

    private final String description;

    TodoStatus(String description) {
        this.description = description;
    }

    @JsonCreator
    public static TodoStatus from(String value) {
        if (value == null) return null;
        try {
            return TodoStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 할 일 상태입니다: " + value);
        }
    }
}
