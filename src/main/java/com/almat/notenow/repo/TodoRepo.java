package com.almat.notenow.repo;

import com.almat.notenow.model.Todo;
import com.almat.notenow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepo extends JpaRepository<Todo, Long> {
    List<Todo> findByAuthor(User user);
}
