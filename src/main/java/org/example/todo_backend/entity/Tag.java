package org.example.todo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 태그 아이디

    private String name;      // 태그 이름

    @OneToMany(mappedBy = "tag")
    private List<TodoTag> todoTags;  // 중간 테이블(TodoTag)과의 관계 설정
}