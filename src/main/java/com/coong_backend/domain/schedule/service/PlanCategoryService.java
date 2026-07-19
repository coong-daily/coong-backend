package com.coong_backend.domain.schedule.service;

import com.coong_backend.domain.schedule.dto.PlanCategoryDto;
import com.coong_backend.domain.schedule.repository.PlanCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanCategoryService {

    private final PlanCategoryRepository planCategoryRepository;

    @Transactional(readOnly = true)
    public List<PlanCategoryDto> findAll() {
        return planCategoryRepository.findAll().stream()
                .map(PlanCategoryDto::from)
                .collect(Collectors.toList());
    }
}