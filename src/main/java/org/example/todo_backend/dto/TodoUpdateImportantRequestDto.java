package org.example.todo_backend.dto;

import lombok.Data;

@Data
public class TodoUpdateImportantRequestDto {
    private Boolean important;  // 중요성 여부
}
