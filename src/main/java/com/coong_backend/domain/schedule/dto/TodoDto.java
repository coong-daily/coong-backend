package com.coong_backend.domain.schedule.dto;

import com.coong_backend.domain.schedule.entity.PlanCategory;
import com.coong_backend.domain.schedule.entity.Todo;
import com.coong_backend.domain.schedule.type.TodoPriority;
import com.coong_backend.domain.schedule.type.TodoStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Long id;
    private String title;
    private TodoStatus status;
    private TodoPriority priority;
    private LocalDate dueDate;
    private LocalDate createdAt;
    private Integer progress;

    private Long categoryId;
    private String categoryName;

    public static TodoDto from(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .dueDate(entity.getDueDate())
                .createdAt(entity.getCreatedAt())
                .progress(entity.getProgress())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getCategoryName() : null)
                .build();
    }

    public Todo toEntity(PlanCategory category) {
        return Todo.builder()
                .title(this.title)
                .status(this.status != null ? this.status : TodoStatus.PENDING)
                .priority(this.priority)
                .dueDate(this.dueDate)
                .progress(this.progress)
                .category(category)
                .build();
        // createdAt은 세팅하지 않음 → @PrePersist가 자동으로 채움
    }
}