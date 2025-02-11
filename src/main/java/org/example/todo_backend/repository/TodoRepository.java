package org.example.todo_backend.repository;

import org.example.todo_backend.entity.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.completed = true AND t.createdAt <= :date")
    List<Todo> findCompletedBefore(@Param("date") LocalDate date);

    List<Todo> findByArchivedTrue();

}
