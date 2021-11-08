package com.example.userfakedatagenerator.controller;

import com.example.userfakedatagenerator.domain.User;
import com.example.userfakedatagenerator.service.FakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
@RequestMapping("/")
public class FakerController {

    @Value("${app.fake.count}")
    private Integer initialCount;

    private final FakerService fakerService;

    @Autowired
    public FakerController(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "locale", defaultValue = "US") String locale,
                        @RequestParam(name = "error", defaultValue = "0.0") double error,
                        @RequestParam(name = "seed", defaultValue = "0") long seed) {

        model.addAttribute("locales", Arrays.asList("US" , "RU", "UK"));
        model.addAttribute("error", error);
        model.addAttribute("seed", seed);

        Page<User> users = fakerService.generateUsers(locale, error, seed, initialCount);
        model.addAttribute("users", users);

        return "index";
    }
}
