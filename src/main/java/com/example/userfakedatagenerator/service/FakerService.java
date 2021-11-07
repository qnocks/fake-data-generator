package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class FakerService {

    private final int ID_LENGTH = 10;

    private final UsersService usersService;

    private final ErrorsService errorsService;

    @Autowired
    public FakerService(UsersService usersService, ErrorsService errorsService) {
        this.usersService = usersService;
        this.errorsService = errorsService;
    }

    public List<User> generateUsers(String locale, double error, long seed, int count) {
        long number1 = 0;
        usersService.clear();

        Faker faker = new Faker(Locale.forLanguageTag(locale), getRandom(seed));

        for (int i = 0; i < count; i++) {

            long number = number1++;
            long id = generateRandomId(faker);
            String fullName = faker.name().nameWithMiddle();
            String address = faker.address().fullAddress();
            String phone = faker.phoneNumber().phoneNumber();

            User user = new User(number, id, fullName, address, phone);

            errorsService.addErrors(user, error, locale);

            usersService.add(user);
        }

        return usersService.getAll();
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
