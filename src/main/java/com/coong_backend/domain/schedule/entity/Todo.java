package com.coong_backend.domain.schedule.entity;

import com.coong_backend.domain.schedule.type.TodoPriority;
import com.coong_backend.domain.schedule.type.TodoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TodoStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TodoPriority priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PlanCategory category;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }
}