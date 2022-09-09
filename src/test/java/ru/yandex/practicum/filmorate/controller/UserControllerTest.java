package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;
    private User user;

    @BeforeEach
    public void BeforeEach(){
        userController = new UserController();
        user = new User();
        user.setName("Вася");
        user.setId(1);
        user.setLogin("Vasja123");
        user.setEmail("Vasja@yandex.ru");
        user.setBirthday(LocalDate.of(2000, 10, 11));
    }

    @Test
    void createUserEmailIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserEmailNonIncludeDog() {
        user.setEmail("Vasjayandex.ru");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginNonSpace() {
        user.setLogin("Vasja 123");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserNameIsBlanc() {
        user.setName("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserBirthdayAfterNow() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }
}