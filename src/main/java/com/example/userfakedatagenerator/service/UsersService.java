package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.repository.UsersPageRepository;
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

    private final UsersPageRepository usersPageRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, UsersPageRepository usersPageRepository) {
        this.usersRepository = usersRepository;
        this.usersPageRepository = usersPageRepository;
    }

    public void add(User user) {
//        usersRepository.add(user);
        usersPageRepository.save(user);
    }

    public List<User> getAllByNumber() {
        return usersPageRepository.findAllByOrderByNumberAsc();
    }

    public Page<User> getAll(Pageable pageable) {
        return usersPageRepository.findAll(pageable);
    }

    public List<User> getAll() {
//        return usersRepository.findAll();
        return usersPageRepository.findAll();
    }

    public boolean isUnique(long id) {
        return getAll().stream().noneMatch(user -> user.getId().equals(id));
    }

    public void clear() {
//        usersRepository.clear();
        usersPageRepository.deleteAll();
    }
}
