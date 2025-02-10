package org.example.todo_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.TodoCreateRequestDto;
import org.example.todo_backend.dto.TodoUpdateCompletedRequestDto;
import org.example.todo_backend.dto.TodoUpdateImportantRequestDto;
import org.example.todo_backend.dto.TodoUpdateRequestDto;
import org.example.todo_backend.entity.Todo;
import org.example.todo_backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "등록", description = "TODO를 등록합니다.")
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Validated @RequestBody TodoCreateRequestDto dto) {
        Todo createdTodo = todoService.createTodo(dto);
        return ResponseEntity.ok(createdTodo);
    }

    @Operation(summary = "내용 수정", description = "TODO를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoUpdateRequestDto dto) {
        Todo updatedTodo = todoService.updateTodo(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "중요성 수정", description = "TODO 중요성 여부를 수정합니다.")
    @PatchMapping("/{id}/important")
    public ResponseEntity<Todo> updateTodoImportant(@PathVariable Long id, @RequestBody TodoUpdateImportantRequestDto dto) {
        Todo updatedTodo = todoService.updateTodoImportant(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "완료 여부 수정", description = "TODO 완료 여부를 수정합니다.")
    @PatchMapping("/{id}/completed")
    public ResponseEntity<Todo> updateTodoCompleted(@PathVariable Long id, @RequestBody TodoUpdateCompletedRequestDto dto) {
        Todo updatedTodo = todoService.updateTodoCompleted(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "정렬조건에 따른 전체 조회", description = "TODO를 등록시간, 중요성, 완료여부에따라 정렬하여 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getAllTodos(
            @RequestParam(defaultValue = "createdAt") String sortBy) {
        List<Todo> todos = todoService.getAllTodosSorted(sortBy);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "상세 조회", description = "특정 TODO를 상세보기 하거나 수정하기 위해 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoDetail(@PathVariable Long id) {
        Todo todo = todoService.getTodoDetail(id);
        return ResponseEntity.ok(todo);
    }

    @Operation(summary = "삭제", description = "특정 TODO를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
