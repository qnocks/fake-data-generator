package com.example.userfakedatagenerator.service;

import com.example.userfakedatagenerator.domain.User;
import com.example.userfakedatagenerator.util.FakerUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

@Service
public class ErrorsService {

    private String locale;

    public User addErrors(User user, double error, String locale) {
        this.locale = locale;

        while (error > 0) {
           double diff = divideError(error, user);
           error -= diff;
        }

        return user;
    }

    private double divideError(double error, User user) {
        if ((error % 1) == 0) {
            processError(user);
            return 1.0;
        }

        if (!((int) error == error)) {
            double fractionalPart = error % 1;
            int bound = (int) (fractionalPart * 100);

            if (getRandom().nextInt(100) < bound) {
                processError(user);
            }

            return fractionalPart;
        }
        return .0;
    }

    private void processError(User user) {
        String field = chooseFieldToError(user);
        String fieldWithError = addError(field, Objects.requireNonNull(chooseError()));

        if (user.getFullName().equals(field)) {
            user.setFullName(fieldWithError);
        }
        else if (user.getAddress().equals(field)) {
            user.setAddress(fieldWithError);
        }
        else if (user.getPhone().equals(field)) {
            user.setPhone(fieldWithError);
        }
    }


    private String addError(String target, Function<String, String> error) {
        return error.apply(target);
    }

    private String deleteSymbol(String input) {
        if (input.length() <= 1) return "";

        StringBuilder sb = new StringBuilder(input);
        sb.deleteCharAt(getRandom().nextInt(input.length()));

        return sb.toString();
    }

    private String addSymbol(String input) {
        if (input.length() <= 1) return "";

        StringBuilder sb = new StringBuilder(input);
        sb.insert(getRandom().nextInt(input.length()), FakerUtil.getRandomChar(locale));

        return sb.toString();
    }

    private String replaceSymbol(String input) {
        if (input.length() <= 1) return "";

        int idx = getRandom().nextInt(input.length() - 1);

        char[] chars = input.toCharArray();

        char tmp = chars[idx + 1];
        chars[idx + 1] = chars[idx];
        chars[idx] = tmp;

        return String.valueOf(chars);
   }

    private String chooseFieldToError(User user) {
        switch (getRandom().nextInt(3)) {
            case 0:
                return user.getFullName();
            case 1:
                return user.getAddress();
            case 2:
                return user.getPhone();
            default:
                return "";
        }
    }

    private Function<String, String> chooseError() {
        switch (getRandom().nextInt(3)) {
            case 0:
                return this::addSymbol;
            case 1:
                return this::deleteSymbol;
            case 2:
                return this::replaceSymbol;
            default:
                return null;
        }
    }

    private Random getRandom() {
        return new Random();
    }
}
