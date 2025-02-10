package org.example.todo_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todo_backend.service.TodoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
}
