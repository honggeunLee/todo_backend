package org.example.todo_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.*;
import org.example.todo_backend.entity.Tag;
import org.example.todo_backend.entity.Todo;
import org.example.todo_backend.entity.TodoTag;
import org.example.todo_backend.exception.TodoNotFoundException;
import org.example.todo_backend.repository.TagRepository;
import org.example.todo_backend.repository.TodoRepository;
import org.example.todo_backend.repository.TodoTagRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TagRepository tagRepository;
    private final TodoTagRepository todoTagRepository;

    // Todo 생성 (태그 포함)
    @Transactional
    public TodoDto createTodoWithTags(TodoCreateRequestDto dto) {
        Todo todo = Todo.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(false)
                .build();

        todo = todoRepository.save(todo);

        if (dto.getTagIds() != null) {
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("Tag not found with id " + tagId));

                TodoTag todoTag = TodoTag.builder()
                        .todo(todo)
                        .tag(tag)
                        .build();

                todoTagRepository.save(todoTag);
            }
        }

        return convertToDto(todo);
    }

    // Todo List 조회
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Todo 상세 조회
    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        return convertToDto(todo);
    }

    // Todo 수정 (태그 포함)
    @Transactional
    public TodoDto updateTodoWithTags(Long id, TodoDto dto, List<Long> tagIds) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        existingTodo.setTitle(dto.getTitle());
        existingTodo.setDescription(dto.getDescription());
        existingTodo.setCompleted(dto.getCompleted());

        // 기존 태그 관계 삭제 후 새로운 태그 설정
        todoTagRepository.deleteAll(todoTagRepository.findByTodoId(id));
        if (tagIds != null) {
            todoTagRepository.deleteAll(todoTagRepository.findByTodoId(id));
            for (Long tagId : tagIds) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("Tag not found with id " + tagId));

                TodoTag todoTag = TodoTag.builder()
                        .todo(existingTodo)
                        .tag(tag)
                        .build();

                todoTagRepository.save(todoTag);
            }
        }


        return convertToDto(existingTodo);
    }

    // Todo 완료 처리
    @Transactional
    public void markTodoAsCompleted(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + todoId));

        todo.setCompleted(true);
        todoRepository.save(todo);
    }


    // Todo 아카이브 처리
    @Transactional
    public void archiveTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + todoId));

        todo.setArchived(true);
        todoRepository.save(todo);
    }

    // Todo 아카이브 조회
    public List<TodoDto> getArchivedTodos() {
        List<Todo> archivedTodos = todoRepository.findByArchivedTrue(); // Repository에서 가져옴
        return archivedTodos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 완료된 Todo 자동 아카이브 (매일 자정 실행)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void archiveCompletedTodos() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        List<Todo> todosToArchive = todoRepository.findCompletedBefore(sevenDaysAgo);
        for (Todo todo : todosToArchive) {
            todo.setArchived(true);
            todoRepository.save(todo);
        }
    }

    // Todo 삭제
    @Transactional
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));

        todoTagRepository.deleteAll(todoTagRepository.findByTodoId(id)); // 연관된 태그 삭제
        todoRepository.delete(todo);
    }

    // Todo에 태그 추가
    @Transactional
    public void addTagToTodo(Long todoId, Long tagId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        TodoTag todoTag = TodoTag.builder()
                .todo(todo)
                .tag(tag)
                .build();

        todoTagRepository.save(todoTag);
    }

    // Todo에서 태그 삭제
    @Transactional
    public void removeTagFromTodo(Long todoId, Long tagId) {
        TodoTag todoTag = todoTagRepository.findByTodoIdAndTagId(todoId, tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found on this Todo"));

        todoTagRepository.delete(todoTag);
    }

    // Todo 태그 업데이트 (기존 태그 삭제 후 새로운 태그 추가)
    @Transactional
    public void updateTagForTodo(Long todoId, Long oldTagId, Long newTagId) {
        removeTagFromTodo(todoId, oldTagId);
        addTagToTodo(todoId, newTagId);
    }

    // Todo DTO 변환 메서드
    public TodoDto convertToDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setDescription(todo.getDescription());
        todoDto.setCompleted(todo.getCompleted());
        todoDto.setCreatedAt(todo.getCreatedAt());

        List<TodoTagDto> todoTagDtos = Optional.ofNullable(todo.getTodoTags())
                .orElse(List.of())
                .stream()
                .map(todoTag -> {
                    TodoTagDto todoTagDto = new TodoTagDto();
                    todoTagDto.setId(todoTag.getId());
                    todoTagDto.setTodoId(todoTag.getTodo().getId());
                    todoTagDto.setTagId(todoTag.getTag().getId());
                    todoTagDto.setTagName(todoTag.getTag().getName());
                    return todoTagDto;
                })
                .collect(Collectors.toList());

        todoDto.setTodoTags(todoTagDtos);

        return todoDto;
    }
}
