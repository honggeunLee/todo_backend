package org.example.todo_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoCreateRequestDto {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;
    private String description;
    private Boolean important = false;
}
