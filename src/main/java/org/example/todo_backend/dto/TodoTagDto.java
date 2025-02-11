package org.example.todo_backend.dto;

import lombok.Data;

@Data
public class TodoTagDto {

    private Long id;
    private Long tagId;
    private Long todoId;
    private String tagName;
}
