package com.coong_backend.domain.diary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Mood")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mood")
    private String mood;

    @Column(name = "img")
    private String img;

    @OneToMany(mappedBy = "mood")
    private List<Diary> diaries;

}
