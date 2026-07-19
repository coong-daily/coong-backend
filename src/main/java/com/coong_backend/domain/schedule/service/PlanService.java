package com.coong_backend.domain.schedule.service;

import com.coong_backend.domain.schedule.dto.PlanDto;
import com.coong_backend.domain.schedule.entity.Plan;
import com.coong_backend.domain.schedule.entity.PlanCategory;
import com.coong_backend.domain.schedule.repository.PlanCategoryRepository;
import com.coong_backend.domain.schedule.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanCategoryRepository planCategoryRepository;

    @Transactional(readOnly = true)
    public List<PlanDto> findPlansByDate(LocalDate day) {
        return findPlansByRange(day, day);
    }

    @Transactional(readOnly = true)
    public List<PlanDto> findPlansByRange(LocalDate rangeStart, LocalDate rangeEnd) {
        LocalDateTime rangeStartAt = rangeStart.atStartOfDay();
        LocalDateTime rangeEndAt = rangeEnd.plusDays(1).atStartOfDay().minusNanos(1);

        List<Plan> plans = planRepository
                .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(rangeEndAt, rangeStartAt);

        return plans.stream()
                .map(PlanDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 일정을 새로 생성합니다.
     */
    @Transactional
    public PlanDto createPlan(PlanDto dto) {
        validateDateRange(dto);

        PlanCategory category = resolveCategory(dto.getCategoryId());

        Plan plan = dto.toEntity(category);
        Plan saved = planRepository.save(plan);
        return PlanDto.from(saved);
    }

    /**
     * 기존 일정을 수정합니다.
     */
    @Transactional
    public PlanDto updatePlan(Long id, PlanDto dto) {
        validateDateRange(dto);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다: " + id));

        PlanCategory category = resolveCategory(dto.getCategoryId());

        plan.setTitle(dto.getTitle());
        plan.setContent(dto.getContent());
        plan.setRepeatType(dto.getRepeatType());
        plan.setRepeatDays(dto.getRepeatDays());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setColor(dto.getColor());
        plan.setPlanCategory(category);

        // JPA 영속성 컨텍스트 내 엔티티라 save() 없이도 트랜잭션 종료 시 자동 반영되지만,
        // 명시적으로 호출해 의도를 분명히 함
        Plan saved = planRepository.save(plan);
        return PlanDto.from(saved);
    }

    /**
     * 일정을 삭제합니다.
     */
    @Transactional
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다: " + id);
        }
        planRepository.deleteById(id);
    }

    private PlanCategory resolveCategory(Long categoryId) {
        if (categoryId == null) return null;
        return planCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정 카테고리입니다: " + categoryId));
    }

    private void validateDateRange(PlanDto dto) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수입니다.");
        }
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("종료일은 시작일보다 빠를 수 없습니다.");
        }
    }
}