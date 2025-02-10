package org.example.todo_backend.dto;

import lombok.Data;

@Data
public class TodoUpdateRequestDto {

    private String title;          // 제목 수정
    private String description;    // 내용 수정
    private Boolean important;     // 중요 여부 수정
    private Boolean completed;     // 완료 여부 수정
}
