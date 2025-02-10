package org.example.todo_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.TodoCreateRequestDto;
import org.example.todo_backend.dto.TodoUpdateCompletedRequestDto;
import org.example.todo_backend.dto.TodoUpdateImportantRequestDto;
import org.example.todo_backend.dto.TodoUpdateRequestDto;
import org.example.todo_backend.entity.Todo;
import org.example.todo_backend.exception.TodoNotFoundException;
import org.example.todo_backend.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // Todo 생성
    public Todo createTodo(TodoCreateRequestDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("TodoCreateRequestDto must not be null");
        }

        Todo todo = Todo.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .important(dto.getImportant() != null ? dto.getImportant() : false)  // null 처리 추가
                .completed(false)
                .build();

        return todoRepository.save(todo);
    }

    // Todo 수정
    public Todo updateTodo(Long id, TodoUpdateRequestDto dto) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        existingTodo.setTitle(dto.getTitle());
        existingTodo.setDescription(dto.getDescription());
        existingTodo.setImportant(dto.getImportant());
        existingTodo.setCompleted(dto.getCompleted());

        return todoRepository.save(existingTodo);
    }

    // Todo 중요도 수정(true, false)
    public Todo updateTodoImportant(Long id, TodoUpdateImportantRequestDto dto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        if (dto.getImportant() == null) {
            throw new IllegalArgumentException("Important flag must not be null");
        }

        todo.setImportant(dto.getImportant());
        return todoRepository.save(todo);
    }

    // Todo 완료 여부 수정(ture, false)
    public Todo updateTodoCompleted(Long id, TodoUpdateCompletedRequestDto dto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        if (dto.getCompleted() == null) {
            throw new IllegalArgumentException("Completed flag must not be null");
        }

        todo.setCompleted(dto.getCompleted());
        return todoRepository.save(todo);
    }

    // 정렬 조건에 따른 Todo 조회
    public List<Todo> getAllTodosSorted(String sortBy) {
        Sort sort = Sort.by(Sort.Order.asc("createdAt")); // 기본 정렬: 등록순

        switch (sortBy) {
            case "createdAt_desc":
                sort = Sort.by(Sort.Order.desc("createdAt")); // 등록순 역순
                break;
            case "completed_asc":
                sort = Sort.by(Sort.Order.asc("completed")); // 완료하지 않은 일 우선
                break;
            case "completed_desc":
                sort = Sort.by(Sort.Order.desc("completed")); // 완료한 일 우선
                break;
            case "important_desc":
                sort = Sort.by(Sort.Order.desc("important")); // 중요한 일 우선
                break;
            case "important_asc":
                sort = Sort.by(Sort.Order.asc("important")); // 중요하지 않은 일 우선
                break;
            default:
                throw new IllegalArgumentException("Invalid sorting criteria");
        }

        return todoRepository.findAll(sort);
    }

    // 특정 Todo 조회
    public Todo getTodoDetail(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));
    }

    // 특정 Todo 삭제
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));
        todoRepository.delete(todo);
    }
}
