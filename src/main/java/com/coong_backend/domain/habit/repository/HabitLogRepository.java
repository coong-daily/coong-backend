package com.coong_backend.domain.habit.repository;

import com.coong_backend.domain.habit.entity.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {

    // 특정 습관의 특정 날짜 범위에 존재하는 로그 찾기
    @Query("SELECT hl FROM HabitLog hl WHERE hl.habit.id = :habitId AND hl.date >= :startDate AND hl.date <= :endDate")
    Optional<HabitLog> findByHabitIdAndDateRange(
            @Param("habitId") Long habitId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}