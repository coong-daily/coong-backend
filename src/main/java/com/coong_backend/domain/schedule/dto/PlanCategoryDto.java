package com.coong_backend.domain.schedule.dto;

import com.coong_backend.domain.schedule.entity.PlanCategory;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanCategoryDto {

    private Long id;
    private String categoryName;

    public static PlanCategoryDto from(PlanCategory entity) {
        return PlanCategoryDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .build();
    }
}