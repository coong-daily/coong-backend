package com.coong_backend.domain.schedule.repository;

import com.coong_backend.domain.schedule.entity.Todo;
import com.coong_backend.domain.schedule.type.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOrderByDueDateAscIdAsc();
    List<Todo> findAllByStatusOrderByDueDateAscIdAsc(TodoStatus status);
}