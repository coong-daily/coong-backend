package com.coong_backend.domain.habit.dto;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitRequestDto {
    private String title;
    private String content; // 설명 영역
    private Integer days;    // 기본값 등으로 프론트와 맞춰 활용 (예: 127 = 매일)
    private String icon;
    private String color;
    private LocalTime time;
}