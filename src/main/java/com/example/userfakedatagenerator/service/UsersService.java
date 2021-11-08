package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.repository.UsersRepository;
import com.example.userfakedatagenerator.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void add(User user) {
        usersRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    public boolean isUnique(long id) {
        return getAll().stream().noneMatch(user -> user.getId().equals(id));
    }

    public void clear() {
        usersRepository.deleteAll();
    }
}
