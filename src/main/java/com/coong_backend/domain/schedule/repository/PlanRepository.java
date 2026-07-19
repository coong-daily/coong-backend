package com.coong_backend.domain.schedule.repository;

import com.coong_backend.domain.schedule.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
            LocalDateTime dayEnd, LocalDateTime dayStart
    );
}
