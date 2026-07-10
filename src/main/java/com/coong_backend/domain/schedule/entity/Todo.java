package com.coong_backend.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private PlanCategory category;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "done_time")
    private LocalDateTime doneTime;

    @Column(name = "start_date")
    private LocalDateTime startDate;

}
