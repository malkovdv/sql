package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.Data;

public class DataHelper {
    private static final Faker FAKER = Faker.instance();

    // Тестовые данные
    public static AuthInfo getAuthInfoTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    // Случайный пользователь
    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    // Случайный код верификации
    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.number().digits(6));
    }

    private static String generateRandomLogin() {
        return FAKER.name().username();
    }

    private static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    // Вложенные классы
    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }
}