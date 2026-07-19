package com.coong_backend.domain.asset.type;

public enum AccountStatus {
    ACTIVE("활동"),
    TERMINATED("해지"),
    DORMANT("휴면"),
    RESTRICTED("거래제한");

    private final String description;

    // 생성자
    AccountStatus(String description) {
        this.description = description;
    }

    // 한글명을 가져오고 싶을 때 사용
    public String getDescription() {
        return description;
    }

    public static AccountStatus from(String value) {
        if (value == null) return null;
        try {
            return AccountStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 계좌 상태입니다: " + value);
        }
    }
}