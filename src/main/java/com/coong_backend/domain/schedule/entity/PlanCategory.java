package com.coong_backend.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Plan_Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "planCategory")
    private List<Plan> plans;

    @OneToMany(mappedBy = "category")
    private List<Todo> todos;


}
