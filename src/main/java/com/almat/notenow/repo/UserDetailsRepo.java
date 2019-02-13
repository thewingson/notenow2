package com.almat.notenow.repo;

import com.almat.notenow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    User findByUsername(String username);
}