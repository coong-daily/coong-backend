package com.coong_backend.domain.habit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Habit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "days")
    private Integer days; // 월화수목금토일을 0/1로 표현해 10진수로 변환한 값

    @Column(name = "icon")
    private String icon;

    @Column(name = "color")
    private String color;

    @Column(name = "time")
    private LocalTime time;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitLog> habitLogs;

}
