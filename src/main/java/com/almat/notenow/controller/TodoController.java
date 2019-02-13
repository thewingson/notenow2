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
@RequestMapping("todo")
public class TodoController {
    private final TodoRepo todoRepo;

    @Autowired
    public TodoController(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @GetMapping
    public List<Todo> list(@AuthenticationPrincipal User currentUser){

        //return todoRepo.findAll();

        return todoRepo.findByAuthor(currentUser);
    }
    /*
    @GetMapping
    public List<Todo> list(@AuthenticationPrincipal User user){

        return todoRepo.findAll();
        //return todoRepo.findByAuthor(user);
    }
*/
    @GetMapping("{id}")//get todo
    public Todo getOne(@PathVariable("id") Todo todo,
                       @AuthenticationPrincipal User user){
        Todo todoItem = new Todo();
        if(todo.getAuthor().getEmail().equals(user.getEmail()))
            todoItem = todo;
        return todoItem;
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

    @PostMapping//create todo
    public Todo create(@RequestBody Todo todo, @AuthenticationPrincipal User user){
        todo.setCreationDate(LocalDateTime.now());
        todo.setAuthor(user);
        return todoRepo.save(todo);
    }

    @PutMapping("{id}")//edit todo
    public Todo update(
            @PathVariable("id") Todo todoFromDb,
            @RequestBody Todo todo,
            @AuthenticationPrincipal User user){

        if(todoFromDb.getAuthor().getEmail().equals(user.getEmail())){
            BeanUtils.copyProperties(todo, todoFromDb, "id");
            todoFromDb.setAuthor(user);
        }
        return todoRepo.save(todoFromDb);
    }

    @DeleteMapping("{id}")//delete todo
    public void delete(@PathVariable("id") Todo todo,
                       @AuthenticationPrincipal User user){
        if(todo.getAuthor().getEmail().equals(user.getEmail()))
            todoRepo.delete(todo);
    }

}
