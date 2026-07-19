package com.coong_backend.domain.schedule.repository;

import com.coong_backend.domain.schedule.entity.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Long> {
}