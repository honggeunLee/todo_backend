package org.example.todo_backend.repository;

import org.example.todo_backend.entity.TodoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoTagRepository extends JpaRepository<TodoTag, Long> {

    // 특정 Todo와 특정 Tag의 연결 관계 조회
    @Query("SELECT tt FROM TodoTag tt WHERE tt.todo.id = :todoId AND tt.tag.id = :tagId")
    Optional<TodoTag> findByTodoIdAndTagId(@Param("todoId") Long todoId, @Param("tagId") Long tagId);

    // 특정 Todo에 연결된 TodoTag 관계 조회
    @Query("SELECT tt FROM TodoTag tt WHERE tt.todo.id = :todoId")
    List<TodoTag> findByTodoId(@Param("todoId") Long todoId);
}
