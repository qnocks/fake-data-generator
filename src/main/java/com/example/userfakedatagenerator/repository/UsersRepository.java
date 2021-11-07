package com.example.userfakedatagenerator.repository;

import com.example.userfakedatagenerator.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRepository {

    private final List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public List<User> findAll() {
        return users;
    }

    public void clear() {
        users.clear();
    }
}
