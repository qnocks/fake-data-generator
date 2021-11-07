package com.example.userfakedatagenerator.controller;

import com.example.userfakedatagenerator.domain.User;
import com.example.userfakedatagenerator.repository.UsersPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UsersRestController {

    private final UsersPageRepository usersPageRepository;

    @Autowired
    public UsersRestController(UsersPageRepository usersPageRepository) {
        this.usersPageRepository = usersPageRepository;
    }

//    @GetMapping
//    public Page<User>
//    list(@PageableDefault(sort = {"number"}, direction = Sort.Direction.ASC, size = 20) Pageable pageable) {
//        return usersPageRepository.findAll(pageable);
//    }

    @GetMapping
    public Page<User> list(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("number").ascending());
        return usersPageRepository.findAll(pageable);
    }

}
