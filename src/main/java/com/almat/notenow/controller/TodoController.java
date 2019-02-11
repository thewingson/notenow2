package com.almat.notenow.controller;

import com.almat.notenow.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("todo")
public class TodoController {
    private int count = 4;
    private List<Map<String, String>> todos = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "lingard"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "rasford"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "martial"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return todos;
    }

    @GetMapping("{id}")//get todo
    public Map<String, String> getOne(@PathVariable String id){
        return getTodo(id);
    }

    private Map<String, String> getTodo(@PathVariable String id) {
        return todos.stream()
                .filter(todos -> todos.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping//create todo
    public Map<String, String> create(@RequestBody Map<String, String> todo){
        todo.put("id", String.valueOf(count++));

        todos.add(todo);

        return todo;
    }

    @PutMapping("{id}")//edit todo
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> todo){
        Map<String, String> todoFromDb = getTodo(id);

        todoFromDb.putAll(todo);

        todoFromDb.put("id", id);

        return todoFromDb;
    }

    @DeleteMapping("{id}")//delte todo
    public void delete(@PathVariable String id){
        Map<String, String> todo = getTodo(id);

        todos.remove(todo);
    }

}
