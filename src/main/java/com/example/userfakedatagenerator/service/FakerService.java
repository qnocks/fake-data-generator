package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FakerService {

    @Value("${app.pagination.size}")
    private Integer pageSize;

    private final int ID_LENGTH = 10;

    private long number = 0;

    private final UsersService usersService;

    private final ErrorsService errorsService;

    @Autowired
    public FakerService(UsersService usersService, ErrorsService errorsService) {
        this.usersService = usersService;
        this.errorsService = errorsService;
    }

    public Page<User> generateUsers(String locale, double error, long seed, int count) {
        refreshData();
        Faker faker = new Faker(Locale.forLanguageTag(locale), getRandom(seed));

        for (int i = 0; i < count; i++) {
            User user = generate(faker);
            User userWithError = errorsService.addErrors(user, error, locale);
            usersService.add(userWithError);
        }

        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("number").ascending());
        return usersService.getAll(pageable);
    }

    private void refreshData() {
        usersService.clear();
        number = 0;
    }

    private User generate(Faker faker) {
        long id = generateRandomId(faker);
        String fullName = faker.name().nameWithMiddle();
        String address = faker.address().streetAddress();
        String phone = faker.phoneNumber().phoneNumber();

        return new User(number++, id, fullName, address, phone);
    }

    private long generateRandomId(Faker faker) {
        long id = Long.parseLong(faker.number().digits(ID_LENGTH));

        while (!usersService.isUnique(id)) {
            id = Long.parseLong(faker.number().digits(ID_LENGTH));
        }

        return id;
    }

    private Random getRandom(long seed) {
        return new Random(seed);
    }
}
