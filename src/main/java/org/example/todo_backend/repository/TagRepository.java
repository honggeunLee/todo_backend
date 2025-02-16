package org.example.todo_backend.repository;

import org.example.todo_backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    // 태그 이름으로 조회
    @Query("SELECT t FROM Tag t WHERE t.name = :name")
    Optional<Tag> findByName(@Param("name") String name);
}
