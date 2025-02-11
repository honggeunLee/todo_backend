package org.example.todo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 중간 테이블 아이디

    @ManyToOne
    @JoinColumn(name = "todo_id")  // Todo와의 관계 설정
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "tag_id")   // Tag와의 관계 설정
    private Tag tag;
}
