package com.example.userfakedatagenerator.controller;

import com.example.userfakedatagenerator.domain.User;
import com.example.userfakedatagenerator.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersRestController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersRestController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public Page<User> list(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(name = "size", required = false, defaultValue = "${app.pagination.size}") Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("number").ascending());
        return usersRepository.findAll(pageable);
    }

}
