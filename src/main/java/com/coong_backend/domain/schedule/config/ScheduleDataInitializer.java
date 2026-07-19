package com.coong_backend.domain.schedule.config;

import com.coong_backend.domain.schedule.entity.PlanCategory;
import com.coong_backend.domain.schedule.repository.PlanCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleDataInitializer implements CommandLineRunner {

    private final PlanCategoryRepository planCategoryRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (planCategoryRepository.count() > 0) {
            log.info("이미 일정 카테고리 목데이터가 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("일정 카테고리 목데이터 초기화를 시작합니다.");
        initPlanCategories();
        log.info("일정 카테고리 목데이터 초기화가 완료되었습니다.");
    }

    private void initPlanCategories() {
        planCategoryRepository.save(PlanCategory.builder().categoryName("업무").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("미팅").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("개인").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("취미").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("운동").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("여행").build());
        planCategoryRepository.save(PlanCategory.builder().categoryName("기타").build());
    }
}