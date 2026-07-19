package com.coong_backend.domain.schedule.controller;

import com.coong_backend.domain.schedule.dto.TodoDto;
import com.coong_backend.domain.schedule.service.TodoService;
import com.coong_backend.domain.schedule.type.TodoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoDto>> getTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto dto) {
        TodoDto saved = todoService.createTodo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.updateTodo(id, dto));
    }

    /**
     * 상태만 변경. body 예: { "status": "COMPLETED" }
     */
    @PatchMapping("/todos/{id}/status")
    public ResponseEntity<TodoDto> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TodoStatus status = TodoStatus.from(body.get("status"));
        return ResponseEntity.ok(todoService.updateStatus(id, status));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}