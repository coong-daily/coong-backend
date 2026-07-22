package com.coong_backend.domain.habit.dto;

import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer days;
    private String icon;
    private String color;
    private LocalTime time;
    private List<HabitLogResponseDto> habitLogs;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HabitLogResponseDto {
        private Long id;
        private String date; // 프론트엔드 Key 포맷과 맞추기 위해 yyyy-MM-dd 스트링 변환 추천
        private String status; // "completed", "partial", "missed"
    }
}