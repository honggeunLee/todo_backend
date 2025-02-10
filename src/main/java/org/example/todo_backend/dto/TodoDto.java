package org.example.todo_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoDto {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Boolean important;
}
