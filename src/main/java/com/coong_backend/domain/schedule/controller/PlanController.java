package com.coong_backend.domain.schedule.controller;

import com.coong_backend.domain.schedule.dto.PlanCategoryDto;
import com.coong_backend.domain.schedule.dto.PlanDto;
import com.coong_backend.domain.schedule.service.PlanCategoryService;
import com.coong_backend.domain.schedule.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final PlanCategoryService planCategoryService;

    @GetMapping("/plans")
    public ResponseEntity<List<PlanDto>> getPlansByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<PlanDto> plans = planService.findPlansByRange(startDate, endDate);
        return ResponseEntity.ok(plans);
    }

    /**
     * 일정 생성
     */
    @PostMapping("/plans")
    public ResponseEntity<PlanDto> createPlan(@RequestBody PlanDto dto) {
        PlanDto saved = planService.createPlan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 일정 수정
     */
    @PutMapping("/plans/{id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable Long id, @RequestBody PlanDto dto) {
        PlanDto updated = planService.updatePlan(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/plans/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 일정 카테고리 목록 조회 (일정 추가 모달의 선택 박스용)
     */
    @GetMapping("/categories")
    public ResponseEntity<List<PlanCategoryDto>> getCategories() {
        return ResponseEntity.ok(planCategoryService.findAll());
    }

}