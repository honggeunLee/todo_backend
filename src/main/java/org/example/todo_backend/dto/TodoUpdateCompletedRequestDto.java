package org.example.todo_backend.dto;

import lombok.Data;

@Data
public class TodoUpdateCompletedRequestDto {
    private Boolean completed; // 완료 여부
}
