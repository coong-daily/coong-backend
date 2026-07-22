package com.coong_backend.domain.habit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitLogToggleRequestDto {
    private String date;   // "yyyy-MM-dd"
    private String status; // "completed", "partial", "missed"
}