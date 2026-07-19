package com.coong_backend.domain.schedule.dto;

import com.coong_backend.domain.schedule.entity.Plan;
import com.coong_backend.domain.schedule.entity.PlanCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {

    private Long id;
    private String title;
    private String content;

    private String repeatType; // 예: NONE, DAILY, WEEKLY, MONTHLY
    private String repeatDays; // 예: "MON,WED,FRI"

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String color;

    // 연관관계는 프론트에서 다루기 쉽도록 FK(id)만 노출
    private Long categoryId;
    private String categoryName;

    // entity -> dto
    public static PlanDto from(Plan entity) {
        return PlanDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .repeatType(entity.getRepeatType())
                .repeatDays(entity.getRepeatDays())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .color(entity.getColor())
                .categoryId(entity.getPlanCategory() != null ? entity.getPlanCategory().getId() : null)
                .categoryName(entity.getPlanCategory() != null ? entity.getPlanCategory().getCategoryName() : null)
                .build();
    }

    // dto -> entity
    public Plan toEntity(PlanCategory planCategory) {
        return Plan.builder()
                .title(this.title)
                .content(this.content)
                .repeatType(this.repeatType)
                .repeatDays(this.repeatDays)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .color(this.color)
                .planCategory(planCategory)
                .build();
    }
}