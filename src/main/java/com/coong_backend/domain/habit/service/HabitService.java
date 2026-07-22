package com.coong_backend.domain.habit.service;

import com.coong_backend.domain.habit.dto.*;
import com.coong_backend.domain.habit.entity.Habit;
import com.coong_backend.domain.habit.entity.HabitLog;
import com.coong_backend.domain.habit.repository.HabitRepository;
import com.coong_backend.domain.habit.repository.HabitLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;

    public List<HabitResponseDto> getAllHabits() {
        return habitRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HabitResponseDto createHabit(HabitRequestDto requestDto) {
        Habit habit = Habit.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .days(requestDto.getDays() != null ? requestDto.getDays() : 127) // 기본값 매일
                .icon(requestDto.getIcon())
                .color(requestDto.getColor())
                .time(requestDto.getTime() != null ? requestDto.getTime() : LocalTime.NOON)
                .build();

        Habit savedHabit = habitRepository.save(habit);
        return convertToResponseDto(savedHabit);
    }

    @Transactional
    public void toggleHabitLog(Long habitId, HabitLogToggleRequestDto toggleRequest) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 습관이 존재하지 않습니다. id=" + habitId));

        LocalDate targetDate = LocalDate.parse(toggleRequest.getDate(), DateTimeFormatter.ISO_DATE);
        // 당일 00:00:00 ~ 23:59:59 범위 조회
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

        habitLogRepository.findByHabitIdAndDateRange(habitId, startOfDay, endOfDay)
                .ifPresentOrElse(
                        existingLog -> {
                            // 이미 상태가 존재하면 업데이트 혹은 삭제(미완료 전환 시) 처리
                            if ("missed".equalsIgnoreCase(toggleRequest.getStatus())) {
                                habitLogRepository.delete(existingLog);
                            } else {
                                existingLog.setStatus(toggleRequest.getStatus());
                            }
                        },
                        () -> {
                            // 로그가 없으면 새로 생성하여 저장
                            HabitLog newLog = HabitLog.builder()
                                    .date(startOfDay) // 기준 시간 저장
                                    .status(toggleRequest.getStatus())
                                    .habit(habit)
                                    .build();
                            habitLogRepository.save(newLog);
                        }
                );
    }

    @Transactional
    public HabitResponseDto updateHabit(Long id, HabitRequestDto requestDto) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 습관이 존재하지 않습니다. id=" + id));

        if (requestDto.getTitle() != null) {
            habit.setTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            habit.setContent(requestDto.getContent()); // 프론트엔드의 subtitle 매핑 영역
        }
        if (requestDto.getIcon() != null) {
            habit.setIcon(requestDto.getIcon());
        }
        if (requestDto.getColor() != null) {
            habit.setColor(requestDto.getColor());
        }
        if (requestDto.getTime() != null) {
            habit.setTime(requestDto.getTime());
        }
        if (requestDto.getDays() != null) {
            habit.setDays(requestDto.getDays());
        }

        // 수정된 엔터티를 다시 응답 DTO로 변환하여 반환
        return convertToResponseDto(habit);
    }

    @Transactional
    public void deleteHabit(Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 습관이 존재하지 않습니다. id=" + id));

        // 엔터티 정의 시 cascade = CascadeType.ALL, orphanRemoval = true 처리가 되어 있으므로
        // 자식 테이블인 Habit_Log의 데이터도 함께 안전하게 자동 삭제됩니다.
        habitRepository.delete(habit);
    }

    private HabitResponseDto convertToResponseDto(Habit habit) {
        List<HabitResponseDto.HabitLogResponseDto> logDtos = habit.getHabitLogs() != null ?
                habit.getHabitLogs().stream()
                        .map(log -> HabitResponseDto.HabitLogResponseDto.builder()
                                .id(log.getId())
                                .date(log.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .status(log.getStatus())
                                .build())
                        .collect(Collectors.toList()) : List.of();

        return HabitResponseDto.builder()
                .id(habit.getId())
                .title(habit.getTitle())
                .content(habit.getContent())
                .days(habit.getDays())
                .icon(habit.getIcon())
                .color(habit.getColor())
                .time(habit.getTime())
                .habitLogs(logDtos)
                .build();
    }
}