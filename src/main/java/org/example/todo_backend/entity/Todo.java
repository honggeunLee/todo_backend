package org.example.todo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 할 일 아이디
    private String title;               // 할 일 제목
    private String description;         // 할 일 내용
    private Boolean completed;          // 완료 여부
    private LocalDateTime createdAt;    // 생성 시간
    private Boolean important = false;  // 중요 업무 구분


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
