package com.example.userfakedatagenerator.util;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class FakerUtil {

    public static char getRandomChar(String locale) {
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        char[] chars = faker.name().name().toCharArray();
        int i = new Random().nextInt(chars.length);
        return chars[i];
    }

}
