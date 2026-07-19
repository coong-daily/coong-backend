package com.coong_backend.domain.schedule.service;

import com.coong_backend.domain.schedule.dto.TodoDto;
import com.coong_backend.domain.schedule.entity.PlanCategory;
import com.coong_backend.domain.schedule.entity.Todo;
import com.coong_backend.domain.schedule.repository.PlanCategoryRepository;
import com.coong_backend.domain.schedule.repository.TodoRepository;
import com.coong_backend.domain.schedule.type.TodoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final PlanCategoryRepository planCategoryRepository;

    @Transactional(readOnly = true)
    public List<TodoDto> findAll() {
        return todoRepository.findAllByOrderByDueDateAscIdAsc().stream()
                .map(TodoDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoDto createTodo(TodoDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }

        PlanCategory category = resolveCategory(dto.getCategoryId());
        Todo todo = dto.toEntity(category);
        Todo saved = todoRepository.save(todo);
        return TodoDto.from(saved);
    }

    @Transactional
    public TodoDto updateTodo(Long id, TodoDto dto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할 일입니다: " + id));

        PlanCategory category = resolveCategory(dto.getCategoryId());

        // 엔티티 필드가 수정되었으므로 정상 작동하게 됩니다.
        todo.setTitle(dto.getTitle());
        todo.setStatus(dto.getStatus());     // getDescription() 제거 (타입 일치)
        todo.setPriority(dto.getPriority()); // getDescription() 제거 (타입 일치)
        todo.setDueDate(dto.getDueDate());
        todo.setProgress(dto.getProgress());
        todo.setCategory(category);

        // Dirty Checking(변경 감지) 덕분에 사실 save를 명시적으로 호출하지 않아도 트랜잭션 종료 시 반영됩니다.
        return TodoDto.from(todo);
    }

    /**
     * 상태만 변경 (체크박스 토글, 드래그 앤 드롭 등에 사용)
     */
    @Transactional
    public TodoDto updateStatus(Long id, TodoStatus status) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할 일입니다: " + id));

        todo.setStatus(status);
        if (status == TodoStatus.COMPLETED) {
            todo.setProgress(100);
        }

        return TodoDto.from(todoRepository.save(todo));
    }

    @Transactional
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 할 일입니다: " + id);
        }
        todoRepository.deleteById(id);
    }

    private PlanCategory resolveCategory(Long categoryId) {
        if (categoryId == null) return null;
        return planCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryId));
    }
}