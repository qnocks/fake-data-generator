package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FakerService {

    private final int ID_LENGTH = 10;

    private final UsersService usersService;

    private final ErrorsService errorsService;

    private final RestTemplate restTemplate;

    @Autowired
    public FakerService(UsersService usersService, ErrorsService errorsService, RestTemplate restTemplate) {
        this.usersService = usersService;
        this.errorsService = errorsService;
        this.restTemplate = restTemplate;
    }

    public Page<User> generateUsers(String locale, double error, long seed, int count) {

        long number = 0;
        usersService.clear();

        Faker faker = new Faker(Locale.forLanguageTag(locale), getRandom(seed));

        for (int i = 0; i < count; i++) {

            long id = generateRandomId(faker);
            String fullName = faker.name().nameWithMiddle();
            String address = faker.address().streetAddress();
            String phone = faker.phoneNumber().phoneNumber();

            User user = new User(number++, id, fullName, address, phone);

            User userWithError = errorsService.addErrors(user, error, locale);

            usersService.add(userWithError);
        }

        Pageable pageable = PageRequest.of(0, 20);
        return usersService.getAll(pageable);
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
