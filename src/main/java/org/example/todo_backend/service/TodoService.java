package org.example.todo_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.todo_backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;


}
