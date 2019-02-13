package com.almat.notenow.controller;

import com.almat.notenow.model.Todo;
import com.almat.notenow.model.User;
import com.almat.notenow.model.Views;
import com.almat.notenow.repo.TodoRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {
    private final TodoRepo todoRepo;

    @Autowired
    public UserController(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @GetMapping
    public List<Todo> list(@AuthenticationPrincipal User currentUser){

        return todoRepo.findByAuthor(currentUser);
    }
/*
    @GetMapping("/user-todos/{id}")
    public List<Todo> userTodos(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) Todo todo
    ) {
        return todoRepo.findByAuthor(user);
    }*/


}
