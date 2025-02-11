package org.example.todo_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.*;
import org.example.todo_backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "TODO 등록", description = "Todo를 등록하면서 태그를 등록할 수 있습니다.")
    @PostMapping
    public ResponseEntity<TodoDto> createTodoWithTags(@RequestBody TodoCreateRequestDto dto) {
        TodoDto createdTodo = todoService.createTodoWithTags(dto);
        return ResponseEntity.ok(createdTodo);
    }

    @Operation(summary = "Todo 수정", description = "Todo의 제목, 내용, 중요성, 완료여부를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodoWithTags(
            @PathVariable Long id,
            @RequestBody TodoDto dto,
            @RequestParam List<Long> tagIds) {
        TodoDto updatedTodo = todoService.updateTodoWithTags(id, dto, tagIds);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "Todo 완료 처리", description = "Todo를 완료 처리합니다.")
    @PatchMapping("/{id}/completed")
    public ResponseEntity<Void> markTodoAsCompleted(@PathVariable Long id) {
        todoService.markTodoAsCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Todo 아카이브 처리", description = "Todo를 아카이브 처리합니다.")
    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archiveTodo(@PathVariable Long id) {
        todoService.archiveTodo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 Todo 조회", description = "전체 Todo를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Todo 아카이브 조회", description = "아카이브에 등록된 Todo만 조회합니다.")
    @GetMapping("/archived")
    public ResponseEntity<List<TodoDto>> getArchivedTodos() {
        return ResponseEntity.ok(todoService.getArchivedTodos());
    }

    @Operation(summary = "특정 Todo 조회", description = "특정 Todo를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoDetail(@PathVariable Long id) {
        TodoDto todoDto = todoService.getTodoById(id);
        return ResponseEntity.ok(todoDto);
    }

    @Operation(summary = "Todo 삭제", description = "Todo를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Todo에 태그 추가", description = "Todo에 태그를 추가합니다.")
    @PostMapping("/{todoId}/tags/{tagId}")
    public ResponseEntity<Void> addTagToTodo(@PathVariable Long todoId, @PathVariable Long tagId) {
        todoService.addTagToTodo(todoId, tagId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Todo에서 태그 삭제", description = "Todo에서 태그를 삭제합니다.")
    @DeleteMapping("/{todoId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromTodo(@PathVariable Long todoId, @PathVariable Long tagId) {
        todoService.removeTagFromTodo(todoId, tagId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Todo 태그 업데이트", description = "Todo의 태그를 업데이트합니다.")
    @PutMapping("/{todoId}/tags")
    public ResponseEntity<Void> updateTagForTodo(@PathVariable Long todoId,
                                                 @RequestParam Long oldTagId,
                                                 @RequestParam Long newTagId) {
        todoService.updateTagForTodo(todoId, oldTagId, newTagId);
        return ResponseEntity.ok().build();
    }
}
