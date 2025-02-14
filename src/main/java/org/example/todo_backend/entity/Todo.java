package org.example.todo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "todo-with-tags", attributeNodes = @NamedAttributeNode("todoTags"))
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 할 일 아이디
    private String title;               // 할 일 제목
    private String description;         // 할 일 내용
    private LocalDateTime createdAt;    // 생성 시간

    @Builder.Default
    private Boolean completed = false;  // 완료 여부

    @Builder.Default
    private Boolean archived = false;   // 아카이브 저장 유무

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY)
    private List<TodoTag> todoTags;     // 중간 테이블(TodoTag)과의 관계 설정

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
