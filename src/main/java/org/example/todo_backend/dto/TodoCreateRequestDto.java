package org.example.todo_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TodoCreateRequestDto {

    private String title;
    private String description;
    private List<Long> tagIds;
}
