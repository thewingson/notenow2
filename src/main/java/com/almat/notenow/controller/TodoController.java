package com.almat.notenow.controller;

import com.almat.notenow.model.Todo;
import com.almat.notenow.repo.TodoRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TodoController {
    private final TodoRepo todoRepo;

    @Autowired
    public TodoController(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @GetMapping
    public List<Todo> list(){

        return todoRepo.findAll();
    }

    @GetMapping("{id}")//get todo
    public Todo getOne(@PathVariable("id") Todo todo){
        return todo;
    }

    @PostMapping//create todo
    public Todo create(@RequestBody Todo todo){
        todo.setCreationDate(LocalDateTime.now());
        return todoRepo.save(todo);
    }

    @PutMapping("{id}")//edit todo
    public Todo update(
            @PathVariable("id") Todo todoFromDb,
            @RequestBody Todo todo){

        BeanUtils.copyProperties(todo, todoFromDb, "id");

        return todoRepo.save(todoFromDb);
    }

    @DeleteMapping("{id}")//delte todo
    public void delete(@PathVariable("id") Todo todo){
        todoRepo.delete(todo);
    }

}
