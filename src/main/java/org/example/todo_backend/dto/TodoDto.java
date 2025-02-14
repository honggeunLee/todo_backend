package org.example.todo_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TodoDto {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Boolean archived;
    private LocalDateTime createdAt;
    private List<TodoTagDto> todoTags;
}
