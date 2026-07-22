package com.coong_backend.domain.habit.controller;

import com.coong_backend.domain.habit.dto.*;
import com.coong_backend.domain.habit.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    // 전체 습관 데이터 가져오기 (초기 화면 로드)
    @GetMapping
    public ResponseEntity<List<HabitResponseDto>> getAllHabits() {
        return ResponseEntity.ok(habitService.getAllHabits());
    }

    // 새로운 습관 추가하기
    @PostMapping
    public ResponseEntity<HabitResponseDto> createHabit(@RequestBody HabitRequestDto requestDto) {
        return ResponseEntity.ok(habitService.createHabit(requestDto));
    }

    // 특정 습관의 특정 일자 로그 상태 토글 / 변경
    @PostMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleHabitLog(
            @PathVariable("id") Long habitId,
            @RequestBody HabitLogToggleRequestDto toggleRequestDto) {

        System.out.println("hi? ?");
        habitService.toggleHabitLog(habitId, toggleRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponseDto> updateHabit(
            @PathVariable("id") Long id,
            @RequestBody HabitRequestDto requestDto) {
        return ResponseEntity.ok(habitService.updateHabit(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable("id") Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.ok().build();
    }
}