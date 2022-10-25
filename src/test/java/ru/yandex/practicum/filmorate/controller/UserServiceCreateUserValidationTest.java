package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceCreateUserValidationTest {

    private final UserService userService;
    private User user;

    @BeforeEach
    public void BeforeEach(){
        user = new User(1, "Вася", "Vasja123", "Vasja@yandex.ru", LocalDate.of(2000, 10, 11));
    }

    @Test
    void createUserEmailIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserEmailNonIncludeDog() {
        user.setEmail("Vasjayandex.ru");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginNonSpace() {
        user.setLogin("Vasja 123");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserBirthdayAfterNow() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userService.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }
}